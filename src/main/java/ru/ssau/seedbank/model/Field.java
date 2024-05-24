package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "field_id")
    private Long fieldId;  // ID виддимости

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "fields")
    private Set<Seed> seeds;

    String field;               // поле

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(fieldId, field.fieldId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId);
    }

}
