package ru.ssau.seedbank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.Seed;

public interface SeedRepository extends JpaRepository<Seed, Integer> {

    @Query("SELECT s FROM Seed s " +
            "WHERE (:specie IS NULL OR s.specie.nameOfSpecie LIKE :specie) " +
            "AND (:genus IS NULL OR s.specie.genus.nameOfGenus LIKE :genus) " +
            "AND (:family IS NULL OR s.specie.genus.family.nameOfFamily LIKE :family)")
    Page<Seed> findSeedsBySpecieAndGenusAndFamily(@Param("specie") String specie, @Param("genus") String genus, @Param("family") String family, Pageable pageable);

}
