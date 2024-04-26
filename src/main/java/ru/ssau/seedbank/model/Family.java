package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer familyId;       // ID семейства

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "family")
    private Set<Genus> genuses;

    private String nameOfFamily;    // название семейства

}
