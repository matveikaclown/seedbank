package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Field;
import ru.ssau.seedbank.model.Seed;

import java.util.Set;

public interface FieldRepository extends JpaRepository<Field, Long> {

    Set<Field> findAllBySeedsContains(Seed seed);

}
