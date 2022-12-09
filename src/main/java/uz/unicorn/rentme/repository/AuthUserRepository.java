package uz.unicorn.rentme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.repository.base.BaseRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, BaseRepository {

    AuthUser findByPhoneNumber(String phoneNumber);

}
