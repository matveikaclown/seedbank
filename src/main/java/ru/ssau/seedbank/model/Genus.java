package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "genus")
public class Genus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genusId;      // ID рода

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "family_id")
    private Family family;      // ID семейства

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genus")
    private Set<Specie> species;

    private String nameOfGenus;   // название рода

}
