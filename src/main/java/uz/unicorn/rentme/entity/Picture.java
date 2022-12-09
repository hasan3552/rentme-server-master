package uz.unicorn.rentme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.unicorn.rentme.entity.base.Auditable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Picture extends Auditable {

    private String path;

    private Boolean main;

    @ManyToOne
    @JoinColumn
    private Advertisement advertisement;

}
