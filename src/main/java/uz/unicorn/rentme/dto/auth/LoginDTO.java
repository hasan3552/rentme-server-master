package uz.unicorn.rentme.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    @Pattern(regexp = "^\\+\\d{12}(\\d{2})?$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotBlank
    private String code;
}
