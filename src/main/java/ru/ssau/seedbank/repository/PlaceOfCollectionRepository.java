package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.PlaceOfCollection;

import java.util.Optional;

public interface PlaceOfCollectionRepository extends JpaRepository<PlaceOfCollection, Integer> {

    Optional<PlaceOfCollection> findPlaceOfCollectionByPlaceOfCollection(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.place_of_collection.placeOfCollectionId = :placeOfCollectionId")
    boolean existsAnySeed(@Param("placeOfCollectionId") Integer placeOfCollectionId);

}
