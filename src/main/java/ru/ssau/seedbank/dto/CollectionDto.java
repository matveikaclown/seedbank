package ru.ssau.seedbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CollectionDto {

    Integer id;
    String family;
    String genus;
    String specie;
    String redBookRF;
    String redList;

}
