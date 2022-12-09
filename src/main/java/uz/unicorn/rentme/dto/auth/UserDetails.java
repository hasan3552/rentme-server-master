package uz.unicorn.rentme.dto.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import uz.unicorn.rentme.entity.AuthUser;
import uz.unicorn.rentme.enums.auth.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final AuthUser authUser;

    public UserDetails(AuthUser authUser) {
        this.authUser = authUser;
    }


    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_" + authUser.getRole())));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !authUser.getStatus().equals(Status.BLOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
