package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.Ecotop;

import java.util.Optional;

public interface EcotopRepository extends JpaRepository<Ecotop, Integer> {

    Optional<Ecotop> findEcotopByNameOfEcotop(String name);

}
