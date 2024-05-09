package ru.ssau.seedbank.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.dto.AtlasDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class PhotoService {

    public String encodeBase64(String path) {
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch(IOException e) {
            System.out.println(e.getMessage());
            bytes = new byte[0];
        }
        return Base64.encodeBase64String(bytes);
    }

    public HashMap<String, String> getAllPhotos(Page<AtlasDto> seeds) {
        HashMap<String, String> photos = new HashMap<>();
        for (AtlasDto seed : seeds) {
            try {
                photos.put(seed.getId(), Base64.encodeBase64String(Files.readAllBytes(Paths.get("images\\" + seed.getId().toString() + "\\seed.jpg"))));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                photos.put(seed.getId(), Base64.encodeBase64String(new byte[0]));
            }
        }
        return photos;
    }

}
