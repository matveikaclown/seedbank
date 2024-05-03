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

    private static Page<CollectionDto> getCollectionDtos(Page<Seed> page) {
        return page.map(seed -> {
            CollectionDto collectionDto = new CollectionDto();
            collectionDto.setId(seed.getSeedId());

            if (seed.getSpecie() != null) {
                collectionDto.setSpecie(seed.getSpecie().getNameOfSpecie());

                if (seed.getSpecie().getGenus() != null) {
                    collectionDto.setGenus(seed.getSpecie().getGenus().getNameOfGenus());

                    if (seed.getSpecie().getGenus().getFamily() != null) {
                        collectionDto.setFamily(seed.getSpecie().getGenus().getFamily().getNameOfFamily());
                    } else {
                        collectionDto.setFamily("");
                    }
                } else {
                    collectionDto.setGenus("");
                    collectionDto.setFamily("");
                }
            } else {
                collectionDto.setSpecie("");
                collectionDto.setGenus("");
                collectionDto.setFamily("");
            }

            if (seed.getRed_book_rf() != null) {
                collectionDto.setRedBookRF(seed.getRed_book_rf().getCategory());
            } else {
                collectionDto.setRedBookRF("");
            }

            if (seed.getRed_list() != null) {
                collectionDto.setRedList(seed.getRed_list().getCategory());
            } else {
                collectionDto.setRedList("");
            }

            return collectionDto;
        });
    }

    public Page<CollectionDto> getAllCollectionSeeds(Pageable pageable) {
        Page<Seed> page = seedRepository.findAll(pageable);
        return getCollectionDtos(page);
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
        return getCollectionDtos(page);
    }

    public SeedDto getSeedById(Integer id) {
        Seed seed = seedRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        SeedDto seedDto = new SeedDto();
        seedDto.setId(seed.getSeedId());
        if (seed.getSpecie() != null) {
            seedDto.setSpecie(seed.getSpecie().getNameOfSpecie());
            if (seed.getSpecie().getGenus() != null) {
                seedDto.setGenus(seed.getSpecie().getGenus().getNameOfGenus());
                if (seed.getSpecie().getGenus().getFamily() != null) {
                    seedDto.setFamily(seed.getSpecie().getGenus().getFamily().getNameOfFamily());
                } else {
                    seedDto.setFamily("");
                }
            } else {
                seedDto.setFamily("");
                seedDto.setGenus("");
            }
        } else {
            seedDto.setFamily("");
            seedDto.setGenus("");
            seedDto.setSpecie("");
        }
        if (seed.getRed_list() != null) {
            seedDto.setRedList(seed.getRed_list().getCategory());
        } else {
            seedDto.setRedList("");
        }
        if (seed.getRed_book_rf() != null) {
            seedDto.setRedBookRF(seed.getRed_book_rf().getCategory());
        } else {
            seedDto.setRedBookRF("");
        }
        if (seed.getRed_book_so() != null) {
            seedDto.setRedBookRF(seed.getRed_book_so().getCategory());
        } else {
            seedDto.setRedBookSO("");
        }
        seedDto.setDateOfCollection(seed.getDateOfCollection());
        if (seed.getPlace_of_collection() != null) {
            seedDto.setPlaceOfCollection(seed.getPlace_of_collection().getPlaceOfCollection());
        } else {
            seedDto.setPlaceOfCollection("");
        }
        seedDto.setWeightOf1000Seeds(seed.getWeightOf1000Seeds());
        seedDto.setNumberOfSeeds(seed.getNumberOfSeeds());
        seedDto.setCompletedSeeds(seed.getCompletedSeeds());
        seedDto.setSeedGermination(seed.getSeedGermination());
        seedDto.setSeedMoisture(seed.getSeedMoisture());
        seedDto.setGPSLatitude(seed.getGPSLatitude());
        seedDto.setGPSLatitude(seed.getGPSLongitude());
        seedDto.setGPSAltitude(seed.getGPSAltitude());
        if(seed.getEcotop() != null) {
            seedDto.setEcotop(seed.getEcotop().getNameOfEcotop());
        } else {
            seedDto.setEcotop("");
        }
        seedDto.setPestInfestation(seed.getPestInfestation());
        seedDto.setComment(seed.getComment());
        return seedDto;
    }

    public void addNewSeed(Integer id) {
        if (seedRepository.findById(id).isEmpty()) {
            Seed seed = new Seed();
            seed.setSeedId(id);
            seedRepository.save(seed);
        }
    }

}
