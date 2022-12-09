package uz.unicorn.rentme.dto.auth;

import lombok.*;
import org.springdoc.api.annotations.ParameterObject;
import uz.unicorn.rentme.dto.base.BaseDTO;
import uz.unicorn.rentme.enums.auth.Gender;
import uz.unicorn.rentme.enums.auth.Language;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ParameterObject
public class AuthUserCreateDTO implements BaseDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{12}(\\d{2})?$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotBlank
    private Language language;

    @Email
    @Pattern(regexp = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$", message = "Email is invalid")
    private String email;

    private Gender gender;

}
