package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.Genus;
import ru.ssau.seedbank.model.Specie;

import java.util.Optional;

public interface SpecieRepository extends JpaRepository<Specie, Integer> {

    Optional<Specie> findSpecieByNameOfSpecieAndGenus(String name, Genus specie);

    Optional<Specie> findSpecieByNameOfSpecie(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.specie.specieId = :specieId")
    boolean existsAnySeed(@Param("specieId") Integer specieId);

}
