package ru.ssau.seedbank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollectionDto {

    Integer id;
    String family;
    String genus;
    String specie;
    String redBookRF;
    String redList;

}
