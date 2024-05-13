package ru.ssau.seedbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.seedbank.model.BookLevel;
import ru.ssau.seedbank.model.RedBook;

import java.util.Optional;

public interface RedBookRepository extends JpaRepository<RedBook, Integer> {

    Optional<RedBook> findRedBookByCategoryAndBookLevel(String name, BookLevel level);

}
