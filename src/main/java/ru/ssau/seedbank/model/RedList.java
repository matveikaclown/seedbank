package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "red_list")
public class RedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId; // ID категории

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "red_list")
    private Set<Seed> seeds;

    private String category;    // категория

}
