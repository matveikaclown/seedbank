package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "seed")
public class Seed {

    @Id
    private Integer seedId;                 // ID семени

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;               // ID личного кабинета

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "specie_id", nullable = false)
    private Specie specie;                // ID вида

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "red_list_id", nullable = false)
    private RedList red_list;       // ID категории красного списка

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "red_book_so_id", nullable = false)
    private RedBook red_book_so;     // ID категории красной книги СО

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "red_book_rf_id", nullable = false)
    private RedBook red_book_rf;     // ID категории красной книги РФ

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "place_of_collection_id", nullable = false)
    private PlaceOfCollection place_of_collection;     // ID места сбора

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "ecotop_id", nullable = false)
    private Ecotop ecotop;                // ID экотопа

    private String seedName;                // название семени
    private Timestamp dateOfCollection;        // дата сбора
    @Column(name = "gpslatitude", length = 20)
    private String GPSLatitude;             // GPS широта
    @Column(name = "gpslongitude", length = 20)
    private String GPSLongitude;            // GPS долгота
    @Column(name = "gpsaltitude", length = 8)
    private String GPSAltitude;             // GPS высота
    @Column(name = "weight_of1000seeds", length = 10)
    private String weightOf1000Seeds;       // вес 1000 семян
    @Column(name = "number_of_seeds", length = 12)
    private String numberOfSeeds;           // количество семян
    @Column(name = "completed_seeds", length = 6)
    private String completedSeeds;          // выполненные семена
    @Column(name = "seed_germination", length = 6)
    private String seedGermination;         // всхожесть семян
    @Column(name = "seed_moisture", length = 6)
    private String seedMoisture;            // влажность семян
    @Column(name = "pest_infestation", length = 6)
    private String pestInfestation;         // заселенность вредителями
    private String photoXRay;               // фото ренгтеннограммы
    private String photoSeed;               // фото семени
    private String photoEcotop;             // фото экотопа
    @Column(name = "comment", columnDefinition = "text")
    private String comment;                 // комментарий

}
