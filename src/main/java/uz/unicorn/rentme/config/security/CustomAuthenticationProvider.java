package uz.unicorn.rentme.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.exceptions.BadRequestException;
import uz.unicorn.rentme.service.auth.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phoneNumber = authentication.getPrincipal().toString();
        String code = authentication.getCredentials().toString();
        List<GrantedAuthority> authorities = new ArrayList<>();
        AuthUser authUser = authService.getUserByPhoneNumber(phoneNumber);
        if (Objects.nonNull(authUser)
                        && phoneNumber.equals(authUser.getPhoneNumber())
                        && code.equals(authUser.getOtp().getCode())) {
            authorities.add(new SimpleGrantedAuthority(authUser.getRole().toString()));
            final UserDetails principal = new User(phoneNumber, code, authorities);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
        throw new RuntimeException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
