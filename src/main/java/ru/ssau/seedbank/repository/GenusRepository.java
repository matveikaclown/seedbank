package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.Family;
import ru.ssau.seedbank.model.Genus;

import java.util.Optional;

public interface GenusRepository extends JpaRepository<Genus, Integer> {

    Optional<Genus> findGenusByNameOfGenusAndFamily(String name, Family family);

    Optional<Genus> findGenusByNameOfGenus(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Specie s WHERE s.genus.genusId = :genusId")
    boolean existsAnySpecie(@Param("genusId") Integer genusId);

}
