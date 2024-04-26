package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.repository.SeedRepository;

@Service
public class SeedService {

    private final SeedRepository seedRepository;

    @Autowired
    public SeedService(SeedRepository seedRepository) {
        this.seedRepository = seedRepository;
    }

    public Page<Seed> getAllSeeds(Pageable pageable) {
        return seedRepository.findAll(pageable);
    }

    public Page<Seed> getAllSeedsByParams(String specie, String genus, String family, Pageable pageable) {
        specie = specie == null ? null : "%" + specie + "%";
        genus = genus == null ? null : "%" + genus + "%";
        family = family == null ? null : "%" + family + "%";
        return seedRepository.findSeedsBySpecieAndGenusAndFamily(specie, genus, family, pageable);
    }

    public Seed getSeedById(Integer id) {
        return seedRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

}
