package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.RedList;

import java.util.Optional;

public interface RedListRepository extends JpaRepository<RedList, Integer> {

    Optional<RedList> findRedListByCategory(String category);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.red_list.categoryId = :redListId")
    boolean existsAnySeed(@Param("redListId") Integer redListId);

}
