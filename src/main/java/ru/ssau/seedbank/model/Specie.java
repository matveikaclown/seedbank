package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "specie")
public class Specie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specieId;       // ID вида

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "genus_id", nullable = false)
    private Genus genus;         // ID рода

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "specie")
    private Set<Seed> seeds;

    private String nameOfSpecie;    // название вида

}
