package ru.ssau.seedbank.service;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.seedbank.model.Field;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.repository.FieldRepository;
import ru.ssau.seedbank.repository.SeedRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhotoServiceTest {

    @Test
    public void test_save_photo_with_non_empty_file() throws IOException {
        PhotoService service = new PhotoService(mock(FieldRepository.class), mock(SeedRepository.class));
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("photo.jpg");
        service.savePhoto(file, "1", "seed", false);
        verify(file, times(1)).transferTo(any(Path.class));
    }

    @Test
    public void test_encode_base64_with_anonymous_and_hidden_field() {
        FieldRepository fieldRepo = mock(FieldRepository.class);
        SeedRepository seedRepo = mock(SeedRepository.class);
        PhotoService service = new PhotoService(fieldRepo, seedRepo);
        Authentication auth = mock(AnonymousAuthenticationToken.class);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(seedRepo.findById("1")).thenReturn(Optional.of(new Seed()));
        Set<Field> fields = new HashSet<>();
        Field field = new Field();
        field.setField("photoXRay");
        fields.add(field);
        when(fieldRepo.findAllBySeedsContains(any())).thenReturn(fields);
        String result = service.encodeBase64("path", "xray", "1");
        assertEquals("", result);
    }

    @Test
    public void test_save_photo_with_empty_file() throws IOException {
        PhotoService service = new PhotoService(mock(FieldRepository.class), mock(SeedRepository.class));
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        service.savePhoto(file, "1", "seed", false);
        verify(file, never()).transferTo(any(Path.class));
    }

}
