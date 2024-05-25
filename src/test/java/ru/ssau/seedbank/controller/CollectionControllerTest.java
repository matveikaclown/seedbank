package ru.ssau.seedbank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.service.CsvService;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CollectionControllerTest {

    @Test
    public void test_fetches_correct_seed_dto() {
        SeedService seedService = mock(SeedService.class);
        when(seedService.getSeedById("123")).thenReturn(new SeedDto());
        CollectionController controller = new CollectionController(seedService, mock(PhotoService.class), mock(CsvService.class));
        Model model = new ExtendedModelMap();
        controller.seed("123", model);
        verify(seedService).getSeedById("123");
    }

    @Test
    public void test_invalid_id_behavior() {
        SeedService seedService = mock(SeedService.class);
        when(seedService.getSeedById("invalid")).thenThrow(new RuntimeException("Seed not found"));
        CollectionController controller = new CollectionController(seedService, mock(PhotoService.class), mock(CsvService.class));
        Model model = new ExtendedModelMap();
        assertThrows(RuntimeException.class, () -> controller.seed("invalid", model));
    }

}
