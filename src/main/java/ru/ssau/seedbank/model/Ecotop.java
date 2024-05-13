package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "ecotop")
public class Ecotop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ecotopId;       // ID экотопа

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ecotop")
    private Set<Seed> seeds;

    private String nameOfEcotop;    // название экотопа

}
