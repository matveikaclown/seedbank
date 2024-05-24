package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.seedbank.model.BookLevel;
import ru.ssau.seedbank.model.RedBook;

import java.util.Optional;

public interface RedBookRepository extends JpaRepository<RedBook, Integer> {

    Optional<RedBook> findRedBookByCategoryAndBookLevel(String name, BookLevel level);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.red_book_rf.categoryId = :redBookRFId")
    boolean existsAnySeedRF(@Param("redBookRFId") Integer redBookRFId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Seed s WHERE s.red_book_so.categoryId = :redBookSOId")
    boolean existsAnySeedSO(@Param("redBookSOId") Integer redBookSOId);

}
