package ru.ssau.seedbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import ru.ssau.seedbank.model.*;
import ru.ssau.seedbank.repository.SeedRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CsvServiceTest {

    @Mock
    private SeedRepository seedRepository;

    @InjectMocks
    private CsvService csvService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExportAllCsv() {
        // Arrange
        Seed seed = new Seed();
        seed.setSeedId("1");
        seed.setSeedName("TestSeed");

        Specie specie = new Specie();
        Genus genus = new Genus();
        Family family = new Family();
        family.setNameOfFamily("TestFamily");
        genus.setNameOfGenus("TestGenus");
        genus.setFamily(family);
        specie.setNameOfSpecie("TestSpecie");
        specie.setGenus(genus);
        seed.setSpecie(specie);

        RedList redList = new RedList();
        redList.setCategory("Endangered");
        seed.setRed_list(redList);

        RedBook redBook = new RedBook();
        redBook.setCategory("category red book");
        seed.setRed_book_rf(redBook);
        seed.setRed_book_so(redBook);

        PlaceOfCollection place = new PlaceOfCollection();
        place.setPlaceOfCollection("place");
        seed.setPlace_of_collection(place);

        Ecotop ecotop = new Ecotop();
        ecotop.setNameOfEcotop("ecotop");
        seed.setEcotop(ecotop);

        List<Seed> seeds = Arrays.asList(seed);
        when(seedRepository.findAll()).thenReturn(seeds);

        // Act
        ResponseEntity<byte[]> response = csvService.exportAllCsv();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(seedRepository, times(1)).findAll();
    }

    @Test
    void testExportSeedCsv() {
        // Arrange
        Seed seed = new Seed();
        seed.setSeedId("1");
        seed.setSeedName("TestSeed");

        Specie specie = new Specie();
        Genus genus = new Genus();
        Family family = new Family();
        family.setNameOfFamily("TestFamily");
        genus.setNameOfGenus("TestGenus");
        genus.setFamily(family);
        specie.setNameOfSpecie("TestSpecie");
        specie.setGenus(genus);
        seed.setSpecie(specie);

        RedList redList = new RedList();
        redList.setCategory("Endangered");
        seed.setRed_list(redList);

        RedBook redBook = new RedBook();
        redBook.setCategory("category red book");
        seed.setRed_book_rf(redBook);
        seed.setRed_book_so(redBook);

        PlaceOfCollection place = new PlaceOfCollection();
        place.setPlaceOfCollection("place");
        seed.setPlace_of_collection(place);

        Ecotop ecotop = new Ecotop();
        ecotop.setNameOfEcotop("ecotop");
        seed.setEcotop(ecotop);
        // Add more setters as needed for the test

        when(seedRepository.findById(seed.getSeedId())).thenReturn(java.util.Optional.of(seed));

        // Act
        ResponseEntity<byte[]> response = csvService.exportSeedCsv(seed.getSeedId());

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(seedRepository, times(1)).findById(seed.getSeedId());
    }
}