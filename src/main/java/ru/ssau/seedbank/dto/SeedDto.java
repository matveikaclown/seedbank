package ru.ssau.seedbank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SeedDto {

    Integer id;
    String family;
    String genus;
    String specie;
    String redList;
    String redBookRF;
    String redBookSO;
    Timestamp dateOfCollection;
    String placeOfCollection;
    String weightOf1000Seeds;
    String numberOfSeeds;
    String completedSeeds;
    String seedGermination;
    String seedMoisture;
    String GPSLatitude;             // GPS широта
    String GPSLongitude;            // GPS долгота
    String GPSAltitude;             // GPS высота
    String ecotop;
    String pestInfestation;
    String comment;

}
