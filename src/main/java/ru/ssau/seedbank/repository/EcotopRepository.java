package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.Ecotop;

import java.util.Optional;

public interface EcotopRepository extends JpaRepository<Ecotop, Integer> {

    Optional<Ecotop> findEcotopByNameOfEcotop(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.ecotop.ecotopId = :ecotopId")
    boolean existsAnySeed(@Param("ecotopId") Integer ecotopId);

}
