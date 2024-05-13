package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Genus;
import ru.ssau.seedbank.model.Specie;

import java.util.Optional;

public interface SpecieRepository extends JpaRepository<Specie, Integer> {

    Optional<Specie> findSpecieByNameOfSpecieAndGenus(String name, Genus specie);

}
