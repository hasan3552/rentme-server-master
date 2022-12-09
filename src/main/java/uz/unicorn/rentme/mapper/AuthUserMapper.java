package uz.unicorn.rentme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;
import uz.unicorn.rentme.dto.auth.AuthUserCreateDTO;
import uz.unicorn.rentme.dto.auth.AuthUserDTO;
import uz.unicorn.rentme.dto.auth.AuthUserUpdateDTO;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.mapper.base.GenericMapper;

@Component
@Mapper( componentModel = "spring" )
public interface AuthUserMapper extends GenericMapper<AuthUser, AuthUserDTO, AuthUserCreateDTO, AuthUserUpdateDTO> {

    @Override
    AuthUser fromCreateDTO(AuthUserCreateDTO dto);

    @Override
    AuthUserDTO toDTO(AuthUser entity);
}
