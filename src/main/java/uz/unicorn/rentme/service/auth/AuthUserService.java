package uz.unicorn.rentme.service.auth;

import org.springframework.stereotype.Service;
import uz.unicorn.rentme.criteria.AuthUserCriteria;
import uz.unicorn.rentme.dto.auth.AuthUserCreateDTO;
import uz.unicorn.rentme.dto.auth.AuthUserDTO;
import uz.unicorn.rentme.dto.auth.AuthUserUpdateDTO;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.entity.Otp;
import uz.unicorn.rentme.enums.auth.AuthRole;
import uz.unicorn.rentme.enums.auth.Status;
import uz.unicorn.rentme.exceptions.NotFoundException;
import uz.unicorn.rentme.mapper.AuthUserMapper;
import uz.unicorn.rentme.repository.AuthUserRepository;
import uz.unicorn.rentme.response.DataDTO;
import uz.unicorn.rentme.response.ResponseEntity;
import uz.unicorn.rentme.service.base.AbstractService;
import uz.unicorn.rentme.service.base.GenericCrudService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthUserService extends AbstractService<AuthUserMapper, AuthUserRepository>
        implements GenericCrudService<AuthUserDTO, AuthUserCreateDTO, AuthUserUpdateDTO, AuthUserCriteria> {
    public AuthUserService(AuthUserMapper mapper, AuthUserRepository repository) {
        super(mapper, repository);
    }

    @Override
    public ResponseEntity<DataDTO<Long>> create(AuthUserCreateDTO dto) {
        AuthUser authUser = mapper.fromCreateDTO(dto);
        Otp otp = Otp
                .builder()
                .code(String.valueOf(UUID.randomUUID()))
                .expiry(LocalDateTime.now().plusMinutes(10))
                .build();
        authUser.setOtp(otp);
        authUser.setRole(AuthRole.USER);
        authUser.setStatus(Status.INACTIVE);
        AuthUser save = repository.save(authUser);
        return new ResponseEntity<>(new DataDTO<>(save.getId()));
    }

    @Override
    public ResponseEntity<DataDTO<Long>> update(AuthUserUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<DataDTO<Boolean>> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<DataDTO<AuthUserDTO>> get(Long id) {
        AuthUser authUser = repository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        AuthUserDTO authUserDTO = mapper.toDTO(authUser);
        return new ResponseEntity<>(new DataDTO<>(authUserDTO));
    }

    @Override
    public ResponseEntity<DataDTO<List<AuthUserDTO>>> getAll(AuthUserCriteria criteria) {
        return null;
    }
}
