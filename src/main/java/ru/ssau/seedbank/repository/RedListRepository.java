package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.RedList;

import java.util.Optional;

public interface RedListRepository extends JpaRepository<RedList, Integer> {

    Optional<RedList> findRedListByCategory(String category);

}
