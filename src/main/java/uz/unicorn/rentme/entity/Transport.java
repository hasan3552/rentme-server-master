package uz.unicorn.rentme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.unicorn.rentme.entity.base.Auditable;
import uz.unicorn.rentme.enums.transport.TransportColor;
import uz.unicorn.rentme.enums.transport.TransportFuel;
import uz.unicorn.rentme.enums.transport.TransportTransmission;
import uz.unicorn.rentme.enums.transport.TransportType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transport extends Auditable {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransportType type;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(columnDefinition = "varchar default 'MANUAL'")
    @Enumerated(value = EnumType.STRING)
    private TransportTransmission transmission;

    @Column(columnDefinition = "varchar default 'PETROL'")
    @Enumerated(value = EnumType.STRING)
    private TransportFuel fuelType;

    @Column(nullable = false)
    private TransportColor color;

    @Column(columnDefinition = "bool default 'false'")
    private Boolean wellEquipped;

}
