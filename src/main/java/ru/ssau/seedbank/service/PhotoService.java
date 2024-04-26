package ru.ssau.seedbank.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.model.Seed;

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

    public HashMap<Integer, String> getAllPhotos(Page<Seed> seeds) {
        HashMap<Integer, String> photos = new HashMap<>();
        for (Seed seed : seeds) {
            try {
                photos.put(seed.getSeedId(), Base64.encodeBase64String(Files.readAllBytes(Paths.get("images\\" + seed.getSeedId().toString() + "\\seed.jpg"))));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                photos.put(seed.getSeedId(), Base64.encodeBase64String(new byte[0]));
            }
        }
        return photos;
    }

}
