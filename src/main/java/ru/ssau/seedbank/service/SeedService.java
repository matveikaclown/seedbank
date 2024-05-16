package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.dto.AtlasDto;
import ru.ssau.seedbank.dto.CollectionDto;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.model.*;
import ru.ssau.seedbank.repository.*;

@Service
public class SeedService {

    private final SeedRepository seedRepository;
    private final FamilyRepository familyRepository;
    private final GenusRepository genusRepository;
    private final SpecieRepository specieRepository;
    private final RedListRepository redListRepository;
    private final RedBookRepository redBookRepository;
    private final PlaceOfCollectionRepository placeOfCollectionRepository;
    private final EcotopRepository ecotopRepository;
    private final BookLevelRepository bookLevelRepository;

    @Autowired
    public SeedService(
            SeedRepository seedRepository,
            FamilyRepository familyRepository,
            GenusRepository genusRepository,
            SpecieRepository specieRepository,
            RedListRepository redListRepository,
            RedBookRepository redBookRepository,
            PlaceOfCollectionRepository placeOfCollectionRepository,
            EcotopRepository ecotopRepository,
            BookLevelRepository bookLevelRepository) {
        this.seedRepository = seedRepository;
        this.familyRepository = familyRepository;
        this.genusRepository = genusRepository;
        this.specieRepository = specieRepository;
        this.redListRepository = redListRepository;
        this.redBookRepository = redBookRepository;
        this.placeOfCollectionRepository = placeOfCollectionRepository;
        this.ecotopRepository = ecotopRepository;
        this.bookLevelRepository = bookLevelRepository;
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

    public SeedDto getSeedById(String id) {
        Seed seed = seedRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        SeedDto seedDto = new SeedDto();
        seedDto.setId(seed.getSeedId());
        seedDto.setSeedName(seed.getSeedName());
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
            seedDto.setRedBookSO(seed.getRed_book_so().getCategory());
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
        seedDto.setGPSLongitude(seed.getGPSLongitude());
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

    public String addNewSeed(String id) {
        if (seedRepository.findById(id).isPresent()) {
            int suffix = 0;
            id += "-";
            do suffix++; while (seedRepository.findById(id + suffix).isPresent());
            id += String.valueOf(suffix);
        }
        Seed seed = new Seed();
        seed.setSeedId(id);
        seedRepository.save(seed);
        return id;
    }

    public void editSeed(SeedDto dto) {
        Seed seed = seedRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Seed not found"));
        // Find or create Family
        Family family = familyRepository.findFamilyByNameOfFamily(dto.getFamily())
                .orElseGet(() -> {
                    Family newFamily = new Family();
                    newFamily.setNameOfFamily(dto.getFamily());
                    return familyRepository.save(newFamily);
                });
        // Find or create Genus
        Genus genus = genusRepository.findGenusByNameOfGenusAndFamily(dto.getGenus(), family)
                .orElseGet(() -> {
                    Genus newGenus = new Genus();
                    newGenus.setNameOfGenus(dto.getGenus());
                    newGenus.setFamily(family);
                    return genusRepository.save(newGenus);
                });
        // Find or create Specie
        Specie specie = specieRepository.findSpecieByNameOfSpecieAndGenus(dto.getSpecie(), genus)
                .orElseGet(() -> {
                    Specie newSpecie = new Specie();
                    newSpecie.setNameOfSpecie(dto.getSpecie());
                    newSpecie.setGenus(genus);
                    return specieRepository.save(newSpecie);
                });
        // Find or create RedList
        RedList redList = redListRepository.findRedListByCategory(dto.getRedList())
                .orElseGet(() -> {
                    RedList newRedList = new RedList();
                    newRedList.setCategory(dto.getRedList());
                    return redListRepository.save(newRedList);
                });
        // Find or create RedBookRF, categoryId = 2
        BookLevel bookLevelRF = bookLevelRepository.findById(2).orElseThrow(() -> new RuntimeException("RF book level not found"));
        RedBook redBookRF = redBookRepository.findRedBookByCategoryAndBookLevel(dto.getRedBookRF(), bookLevelRF)
                .orElseGet(() -> {
                    RedBook newRedBook = new RedBook();
                    newRedBook.setCategory(dto.getRedBookRF());
                    newRedBook.setBookLevel(bookLevelRF);
                    return redBookRepository.save(newRedBook);
                });
        // Find or create RedBookSO, categoryId = 1
        BookLevel bookLevelSO = bookLevelRepository.findById(1).orElseThrow(() -> new RuntimeException("SO book level not found"));
        RedBook redBookSO = redBookRepository.findRedBookByCategoryAndBookLevel(dto.getRedBookSO(), bookLevelSO)
                .orElseGet(() -> {
                    RedBook newRedBook = new RedBook();
                    newRedBook.setCategory(dto.getRedBookSO());
                    newRedBook.setBookLevel(bookLevelSO);
                    return redBookRepository.save(newRedBook);
                });
        // Find or create PlaceOfColelction
        PlaceOfCollection placeOfCollection = placeOfCollectionRepository.findPlaceOfCollectionByPlaceOfCollection(dto.getPlaceOfCollection())
                .orElseGet(() -> {
                    PlaceOfCollection newPlaceOfCollection = new PlaceOfCollection();
                    newPlaceOfCollection.setPlaceOfCollection(dto.getPlaceOfCollection());
                    return placeOfCollectionRepository.save(newPlaceOfCollection);
                });
        // Find or create Ecotop
        Ecotop ecotop = ecotopRepository.findEcotopByNameOfEcotop(dto.getEcotop())
                .orElseGet(() -> {
                    Ecotop newEcotop = new Ecotop();
                    newEcotop.setNameOfEcotop(dto.getEcotop());
                    return ecotopRepository.save(newEcotop);
                });


        seed.setSpecie(specie);
        seed.setRed_list(redList);
        seed.setRed_book_rf(redBookRF);
        seed.setRed_book_so(redBookSO);
        seed.setPlace_of_collection(placeOfCollection);
        seed.setEcotop(ecotop);

        seed.setSeedName(dto.getSeedName());
        seed.setDateOfCollection(dto.getDateOfCollection());
        seed.setWeightOf1000Seeds(dto.getWeightOf1000Seeds());
        seed.setNumberOfSeeds(dto.getNumberOfSeeds());
        seed.setCompletedSeeds(dto.getCompletedSeeds());
        seed.setSeedGermination(dto.getSeedGermination());
        seed.setSeedMoisture(dto.getSeedMoisture());
        seed.setGPSLatitude(dto.getGPSLatitude());
        seed.setGPSLongitude(dto.getGPSLongitude());
        seed.setGPSAltitude(dto.getGPSAltitude());
        seed.setPestInfestation(dto.getPestInfestation());
        seed.setComment(dto.getComment());

        seedRepository.save(seed);
    }

    public void deleteSeed(String id) {
        seedRepository.deleteById(id);
    }

}
