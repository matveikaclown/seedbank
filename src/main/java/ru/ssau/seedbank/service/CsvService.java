package ru.ssau.seedbank.service;

import com.opencsv.CSVWriter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.repository.SeedRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvService {

    private final SeedRepository seedRepository;

    public CsvService(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    private static void getSeedData(Seed seed, String[] data) {
        data[0] = seed.getSeedId();
        data[1] = seed.getSeedName();
        data[2] = seed.getSpecie().getGenus().getFamily().getNameOfFamily();
        data[3] = seed.getSpecie().getGenus().getNameOfGenus();
        data[4] = seed.getSpecie().getNameOfSpecie();
        data[5] = seed.getRed_list().getCategory();
        data[6] = seed.getRed_book_rf().getCategory();
        data[7] = seed.getRed_book_so().getCategory();
        if (seed.getDateOfCollection() != null) data[8] = seed.getDateOfCollection().toString();
        else data[8] = "";
        data[9] = seed.getPlace_of_collection().getPlaceOfCollection();
        data[10] = seed.getWeightOf1000Seeds();
        data[11] = seed.getNumberOfSeeds();
        data[12] = seed.getCompletedSeeds();
        data[13] = seed.getSeedGermination();
        data[14] = seed.getSeedMoisture();
        data[15] = seed.getGPSLatitude() + "/" + seed.getGPSLongitude() + "/" + seed.getGPSAltitude();
        data[16] = seed.getEcotop().getNameOfEcotop();
        data[17] = seed.getPestInfestation();
        data[18] = seed.getComment();
    }

    private static String[] getHeaders() {
        return new String[] {
                "ID", "Название", "Семейство", "Род", "Вид", "Красный список",
                "Красная книга РФ", "Красная книга Самарской области", "Дата сбора",
                "Место сбора", "Масса 1000 семян, г", "Количество семян, шт", "Выполненные семена, %",
                "Всхожесть семян, %", "Влажность семян, %", "GPS, N, E, H", "Экотоп",
                "Заселенность вредителями, %", "Комментарий"
        };
    }

    public ResponseEntity<byte[]> exportAllCsv() {
        List<Seed> seeds = seedRepository.findAll();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error occurred while generating CSV file.".getBytes(StandardCharsets.UTF_8));
        }
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(outputStreamWriter, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            // Заголовки CSV файла
            String[] header = getHeaders();
            csvWriter.writeNext(header);
            String[] data = new String[19];
            for (Seed seed : seeds) {
                getSeedData(seed, data);
                csvWriter.writeNext(data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error occurred while generating CSV file.".getBytes(StandardCharsets.UTF_8));
        }

        byte[] csvContent = byteArrayOutputStream.toByteArray();

        // Устанавливаем необходимые заголовки для загрузки файла
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=seedbank.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }

    public ResponseEntity<byte[]> exportSeedCsv(String id) {
        Seed seed = seedRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not find seed by id " +
                id + " to export"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error occurred while generating CSV file.".getBytes(StandardCharsets.UTF_8));
        }
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(outputStreamWriter, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            // Заголовки CSV файла
            String[] header = getHeaders();
            csvWriter.writeNext(header);
            String[] data = new String[19];
            getSeedData(seed, data);
            csvWriter.writeNext(data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error occurred while generating CSV file.".getBytes(StandardCharsets.UTF_8));
        }

        byte[] csvContent = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + id + ".csv");
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent);
    }

}
