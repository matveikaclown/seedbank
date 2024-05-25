package ru.ssau.seedbank.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import ru.ssau.seedbank.dto.CollectionDto;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.model.Seed;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class SeedServiceTest {

    @Test
    public void test_retrieve_all_seeds_authorized() {
        // Setup
        Pageable pageable = PageRequest.of(0, 10);
        Page<Seed> seedPage = new PageImpl<>(Collections.emptyList());
        SeedService seedService = Mockito.mock(SeedService.class);
        Mockito.when(seedService.getAllCollectionSeeds(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Execution
        Page<CollectionDto> result = seedService.getAllCollectionSeeds(pageable);

        // Verification
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        Mockito.verify(seedService).getAllCollectionSeeds(pageable);
    }

    @Test
    public void test_retrieve_all_seeds_unauthorized() {
        // Setup
        Pageable pageable = PageRequest.of(0, 10);
        Page<Seed> seedPage = new PageImpl<>(Collections.emptyList());
        SeedService seedService = Mockito.mock(SeedService.class);
        Mockito.when(seedService.getAllCollectionSeeds(pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // Execution
        Page<CollectionDto> result = seedService.getAllCollectionSeeds(pageable);

        // Verification
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        Mockito.verify(seedService).getAllCollectionSeeds(pageable);
    }

    @Test
    public void test_retrieve_non_existing_seed() {
        String seedId = "nonExistingId";
        SeedService seedService = Mockito.mock(SeedService.class);
        Mockito.when(seedService.getSeedById(seedId)).thenThrow(new ResourceNotFoundException());
        assertThrows(ResourceNotFoundException.class, () -> seedService.getSeedById(seedId));
    }

    @Test
    public void test_update_non_existing_seed() {
        // Setup
        SeedDto seedDto = new SeedDto();
        seedDto.setId("nonExistingId");
        SeedService seedService = Mockito.mock(SeedService.class);
        Mockito.doThrow(new RuntimeException("Seed not found")).when(seedService).editSeed(seedDto);

        // Execution & Verification
        assertThrows(RuntimeException.class, () -> seedService.editSeed(seedDto));
    }

    @Test
    public void test_delete_non_existing_seed() {
        // Setup
        String seedId = "nonExistingId";
        SeedService seedService = Mockito.mock(SeedService.class);
        Mockito.doThrow(new RuntimeException("Seed not found")).when(seedService).deleteSeed(seedId);

        // Execution & Verification
        assertThrows(RuntimeException.class, () -> seedService.deleteSeed(seedId));
    }

}
