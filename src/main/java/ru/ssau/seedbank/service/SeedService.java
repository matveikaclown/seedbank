package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.dto.AtlasDto;
import ru.ssau.seedbank.dto.CollectionDto;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.repository.SeedRepository;

@Service
public class SeedService {

    private final SeedRepository seedRepository;

    @Autowired
    public SeedService(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    public Page<CollectionDto> getAllCollectionSeeds(Pageable pageable) {
        Page<Seed> page = seedRepository.findAll(pageable);
        return page.map(seed -> new CollectionDto(
                seed.getSeedId(),
                seed.getSpecie().getGenus().getFamily().getNameOfFamily(),
                seed.getSpecie().getGenus().getNameOfGenus(),
                seed.getSpecie().getNameOfSpecie(),
                seed.getRed_book_rf().getCategory(),
                seed.getRed_list().getCategory()
        ));
    }

    public Page<AtlasDto> getAllAtlasSeeds(Pageable pageable) {
        Page<Seed> page = seedRepository.findAll(pageable);
        return page.map(seed -> new AtlasDto(
                seed.getSeedId(),
                seed.getSeedName()
        ));
    }

    public Page<CollectionDto> getAllCollectionSeedsByParams(String specie, String genus, String family, Pageable pageable) {
        specie = specie == null ? null : "%" + specie + "%";
        genus = genus == null ? null : "%" + genus + "%";
        family = family == null ? null : "%" + family + "%";
        Page<Seed> page = seedRepository.findSeedsBySpecieAndGenusAndFamily(specie, genus, family, pageable);
        return page.map(seed -> new CollectionDto(
                seed.getSeedId(),
                seed.getSpecie().getGenus().getFamily().getNameOfFamily(),
                seed.getSpecie().getGenus().getNameOfGenus(),
                seed.getSpecie().getNameOfSpecie(),
                seed.getRed_book_rf().getCategory(),
                seed.getRed_list().getCategory()
        ));
    }

    public SeedDto getSeedById(Integer id) {
        Seed seed = seedRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return new SeedDto(
                seed.getSeedId(),
                seed.getSpecie().getGenus().getFamily().getNameOfFamily(),
                seed.getSpecie().getGenus().getNameOfGenus(),
                seed.getSpecie().getNameOfSpecie(),
                seed.getRed_list().getCategory(),
                seed.getRed_book_rf().getCategory(),
                seed.getRed_book_so().getCategory(),
                seed.getDateOfCollection(),
                seed.getPlace_of_collection().getPlaceOfCollection(),
                seed.getWeightOf1000Seeds(),
                seed.getNumberOfSeeds(),
                seed.getCompletedSeeds(),
                seed.getSeedGermination(),
                seed.getSeedMoisture(),
                seed.getGPSLatitude(),
                seed.getGPSLongitude(),
                seed.getGPSAltitude(),
                seed.getEcotop().getNameOfEcotop(),
                seed.getPestInfestation(),
                seed.getComment()
        );
    }

}
