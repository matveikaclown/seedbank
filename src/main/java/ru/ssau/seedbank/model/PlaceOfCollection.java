package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "place_of_collection")
public class PlaceOfCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer placeOfCollectionId;    // ID места сбора

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place_of_collection")
    private Set<Seed> seeds;

    private String placeOfCollection;       // место сбора

}
