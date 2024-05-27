package ru.ssau.seedbank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
public class SeedDto {

    @NotNull
    private String id;

    private String seedName;
    private String family;
    private String genus;
    private String specie;
    private String redList;
    private String redBookRF;
    private String redBookSO;
    private Date dateOfCollection;
    private String placeOfCollection;
    private String weightOf1000Seeds;
    private String numberOfSeeds;
    private String completedSeeds;
    private String seedGermination;
    private String seedMoisture;
    private String GPSLatitude;             // GPS широта
    private String GPSLongitude;            // GPS долгота
    private String GPSAltitude;             // GPS высота
    private String ecotop;
    private String pestInfestation;
    private String comment;

    private HashMap<String, Boolean> fields;
    private Boolean isHidden;

}
