package uz.unicorn.rentme.entity;

import lombok.*;
import uz.unicorn.rentme.entity.base.Auditable;
import uz.unicorn.rentme.enums.auth.AuthRole;
import uz.unicorn.rentme.enums.auth.Gender;
import uz.unicorn.rentme.enums.auth.Language;
import uz.unicorn.rentme.enums.auth.Status;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class AuthUser extends Auditable {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column
    private String email;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(5) default 'UZ'")
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'INACTIVE'")
    private Status status = Status.INACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(32) default 'USER'")
    private AuthRole role = AuthRole.USER;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "otp_id")
    private Otp otp;

    @OneToMany(mappedBy = "authUser")
    private List<Device> devices;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "auth_user_advertisement",
//            joinColumns = {@JoinColumn(name = "auth_user_id")},
//            inverseJoinColumns = {@JoinColumn(name = "advertisement_id")})
//    private List<Advertisement> savedAdvertisements;

}
