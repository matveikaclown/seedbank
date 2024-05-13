package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.PlaceOfCollection;

import java.util.Optional;

public interface PlaceOfCollectionRepository extends JpaRepository<PlaceOfCollection, Integer> {

    Optional<PlaceOfCollection> findPlaceOfCollectionByPlaceOfCollection(String name);

}
