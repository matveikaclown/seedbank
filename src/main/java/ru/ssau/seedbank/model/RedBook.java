package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "red_book")
public class RedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;     // ID категории

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "book_level_id")
    private BookLevel bookLevel;         // ID уровня книги

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "red_book_so")
    private Set<Seed> seedsSO;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "red_book_rf")
    private Set<Seed> seedsRF;

    private String category;        // категория
    private String description;     // описание

}
