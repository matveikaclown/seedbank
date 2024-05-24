package ru.ssau.seedbank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class CollectionDto {

    private String id;
    private String family;
    private String genus;
    private String specie;
    private String redBookRF;
    private String redList;

    private HashMap<String, Boolean> fields;

}
