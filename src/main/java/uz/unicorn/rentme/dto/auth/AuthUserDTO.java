package uz.unicorn.rentme.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import uz.unicorn.rentme.dto.base.GenericDTO;
import uz.unicorn.rentme.entity.Device;
import uz.unicorn.rentme.entity.Otp;
import uz.unicorn.rentme.enums.auth.AuthRole;
import uz.unicorn.rentme.enums.auth.Gender;
import uz.unicorn.rentme.enums.auth.Language;
import uz.unicorn.rentme.enums.auth.Status;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserDTO extends GenericDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String photo;
    private Language language;
    private Status status;
    private AuthRole role;
    private List<Device> devices;
}
