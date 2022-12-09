package uz.unicorn.rentme;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import uz.unicorn.rentme.dto.auth.AuthUserCreateDTO;
import uz.unicorn.rentme.dto.auth.AuthUserDTO;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.entity.Otp;
import uz.unicorn.rentme.property.ServerProperties;
import uz.unicorn.rentme.repository.AuthUserRepository;
import uz.unicorn.rentme.service.auth.AuthUserService;

@SpringBootApplication
@OpenAPIDefinition
@EnableConfigurationProperties(
        {ServerProperties.class}
)
@RequiredArgsConstructor
public class RentMeApplication {

    private final AuthUserRepository authUserRepository;
    private final AuthUserService authUserService;

    public static void main(String[] args) {
        SpringApplication.run(RentMeApplication.class, args);
    }

//    @Bean
    CommandLineRunner run() {
        return args -> {
            AuthUserCreateDTO dto = AuthUserCreateDTO
                    .builder()
                    .firstName("Admin")
                    .lastName("Root")
                    .phoneNumber("+998901234567")
                    .build();
            authUserService.create(dto);
        };
    }

}
