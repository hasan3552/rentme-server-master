package uz.unicorn.rentme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.unicorn.rentme.enums.DeviceType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceId;

    @Column(nullable = false, unique = true)
    private String deviceToken;

    @Column(nullable = false, columnDefinition = "varchar default 'ANDROID'")
    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn
    private AuthUser authUser;

}
