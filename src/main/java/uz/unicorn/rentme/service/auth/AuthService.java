package uz.unicorn.rentme.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import uz.unicorn.rentme.config.security.utils.JWTUtils;
import uz.unicorn.rentme.dto.auth.LoginDTO;
import uz.unicorn.rentme.dto.auth.SessionDTO;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.property.ServerProperties;
import uz.unicorn.rentme.repository.AuthUserRepository;
import uz.unicorn.rentme.response.AppErrorDTO;
import uz.unicorn.rentme.response.DataDTO;
import uz.unicorn.rentme.response.ResponseEntity;
import uz.unicorn.rentme.service.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthService implements UserDetailsService, BaseService {


    private final AuthUserRepository repository;
    private final ServerProperties serverProperties;
    private final ObjectMapper objectMapper;

    public AuthService(AuthUserRepository repository, ServerProperties serverProperties, ObjectMapper objectMapper) {
        this.repository = repository;
        this.serverProperties = serverProperties;
        this.objectMapper = objectMapper;
    }


    public ResponseEntity<DataDTO<SessionDTO>> getToken(LoginDTO dto) {

        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));
            HttpResponse response = httpclient.execute(httppost);
            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDTO sessionDto = objectMapper.readValue(node.toString(), SessionDTO.class);
                return new ResponseEntity<>(new DataDTO<>(sessionDto), HttpStatus.OK);
            }
            return new ResponseEntity<>(new DataDTO<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppErrorDTO.class)), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new DataDTO<>(AppErrorDTO.builder()
                    .message(e.getLocalizedMessage())
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }


    public ResponseEntity<DataDTO<SessionDTO>> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // gets "Authentication" header from request
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                //token
                String refresh_token = authorizationHeader.substring("Bearer ".length());

                //jwt algorithm
                Algorithm algorithm = JWTUtils.getAlgorithm();

                //checks token to valid  if not valid throws exception
                JWTVerifier verifier = JWT.require(algorithm).build();

                //if valid decode it with given algorithm
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                // from payload part gets subject
                String phoneNumber = decodedJWT.getSubject();

                // gets phone from db
                AuthUser user = getUserByPhoneNumber(phoneNumber);

                //creates new access token
                String access_token = JWT.create()
                        .withSubject(user.getPhoneNumber())
                        .withExpiresAt(JWTUtils.getExpiry())
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", Collections.singletonList(user.getRole().name()))
                        .sign(algorithm);

                //puts into map given refresh and created access tokens
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                //sets response type
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                SessionDTO sessionDto = SessionDTO.builder()
                        .refreshToken(refresh_token)
                        .accessToken(access_token)
                        .build();

                return new ResponseEntity<>(new DataDTO<>(sessionDto));

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            return new ResponseEntity<>(new DataDTO<>(new AppErrorDTO(HttpStatus.NOT_FOUND, "Not Found")));
        }
        return null;
    }

    public AuthUser getUserByPhoneNumber(String phone) {
        log.info("Getting user by phone : {}", phone);
        return repository.findByPhoneNumber(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AuthUser user = repository.findByPhoneNumber(phone);
        return User.builder()
                .username(user.getPhoneNumber())
                .password(user.getOtp().getCode())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }

}
