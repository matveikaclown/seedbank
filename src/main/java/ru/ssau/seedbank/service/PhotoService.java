package ru.ssau.seedbank.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.seedbank.dto.AtlasDto;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

@Service
public class PhotoService {

    private static byte[] findImageBytes(String path) {
        Path pngPath = Paths.get(path + ".png");
        Path jpgPath = Paths.get(path + ".jpg");

        try {
            if (Files.exists(pngPath)) {
                return Files.readAllBytes(pngPath);
            } else if (Files.exists(jpgPath)) {
                return Files.readAllBytes(jpgPath);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return new byte[0];
    }

    private static void deleteDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) return;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    if (!file.delete()) {
                        System.out.println("Не удалось удалить файл: " + file.getAbsolutePath());
                        return;
                    }
                }
            }
        }
        if (!directory.delete()) System.out.println("Не удалось удалить папку: " + directory.getAbsolutePath());
    }

    public String encodeBase64(String path) {
        byte[] bytes = findImageBytes(path);

        return Base64.encodeBase64String(bytes);
    }

    public HashMap<String, String> getAllPhotos(Page<AtlasDto> seeds) {
        HashMap<String, String> photos = new HashMap<>();
        for (AtlasDto seed : seeds) {
            try {
                photos.put(seed.getId(), Base64.encodeBase64String(Files.readAllBytes(Paths.get("images\\" + seed.getId() + "\\seed.jpg"))));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                photos.put(seed.getId(), Base64.encodeBase64String(new byte[0]));
            }
        }
        return photos;
    }

    public void savePhoto(MultipartFile file, String seedId, String fileType, Boolean delete) {
        Path directoryPath = Paths.get("images\\" + seedId); // Определение пути к директории

        if (Boolean.TRUE.equals(delete) || (file != null && !file.isEmpty())) {
            try {
                if (Files.exists(directoryPath)) {
                    try (Stream<Path> files = Files.list(directoryPath)) {
                        files.filter(path -> path.getFileName().toString().startsWith(fileType))
                                .forEach(path -> {
                                    try {
                                        Files.delete(path);
                                    } catch (IOException e) {
                                        throw new RuntimeException("Failed to delete " + fileType + " file", e);
                                    }
                                });
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to list or delete " + fileType + " files", e);
            }
        }

        if (file != null && !file.isEmpty()) {
            try { // Создание директории, если она не существует
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
                // Определение расширения файла
                String extension = "";
                String originalFileName = file.getOriginalFilename();
                if (originalFileName != null && originalFileName.lastIndexOf(".") != -1) {
                    extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                }
                // Создание названия файла в зависимости от типа
                String filename = fileType + extension;
                // Определение пути к файлу
                Path filePath = directoryPath.resolve(filename);
                // Сохранение файла
                file.transferTo(filePath);
            } catch(Exception e){
                throw new RuntimeException("Failed to store " + fileType + " file", e);
            }
        }
    }

    public void deletePhotos(String seedId) {
        File directory = new File("images\\" + seedId);
        deleteDirectory(directory);
    }

}
