package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Family;
import ru.ssau.seedbank.model.Genus;

import java.util.Optional;

public interface GenusRepository extends JpaRepository<Genus, Integer> {

    Optional<Genus> findGenusByNameOfGenusAndFamily(String name, Family family);

}
