package ru.ssau.seedbank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "book_level")
public class BookLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookLevelId;    // ID уровня книги

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book_level")
    private Set<RedBook> redBooks;

    private String levelOfBook;     // уровень книги

}
