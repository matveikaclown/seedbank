package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.Family;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {

    Optional<Family> findFamilyByNameOfFamily(String name);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM Genus g WHERE g.family.familyId = :familyId")
    boolean existsAnyGenus(@Param("familyId") Integer familyId);

}
