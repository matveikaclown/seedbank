package ru.ssau.seedbank.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class FieldsDto {

    private HashMap<String, Boolean> fields;

    public FieldsDto() {
        fields = new HashMap<>();

        fields.put("id", false);
        fields.put("seedName", false);
        fields.put("family", false);
        fields.put("genus", false);
        fields.put("specie", false);
        fields.put("redList", false);
        fields.put("redBookRF", false);
        fields.put("redBookSO", false);
        fields.put("dateOfCollection", false);
        fields.put("placeOfCollection", false);
        fields.put("weightOf1000Seeds", false);
        fields.put("numberOfSeeds", false);
        fields.put("completedSeeds", false);
        fields.put("seedGermination", false);
        fields.put("seedMoisture", false);
        fields.put("GPS", false);
        fields.put("ecotop", false);
        fields.put("pestInfestation", false);
        fields.put("comment", false);
        fields.put("photoXRay", false);
        fields.put("photoSeed", false);
        fields.put("photoEcotop", false);
    }

}
