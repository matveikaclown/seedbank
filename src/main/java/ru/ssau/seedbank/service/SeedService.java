package ru.ssau.seedbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.ssau.seedbank.configuration.websecurity.SeedBankUserDetails;
import ru.ssau.seedbank.dto.CollectionDto;
import ru.ssau.seedbank.dto.FieldsDto;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.model.*;
import ru.ssau.seedbank.repository.*;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final AccountRepository accountRepository;
    private final FieldRepository fieldRepository;

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
            BookLevelRepository bookLevelRepository,
            AccountRepository accountRepository,
            FieldRepository fieldRepository) {
        this.seedRepository = seedRepository;
        this.familyRepository = familyRepository;
        this.genusRepository = genusRepository;
        this.specieRepository = specieRepository;
        this.redListRepository = redListRepository;
        this.redBookRepository = redBookRepository;
        this.placeOfCollectionRepository = placeOfCollectionRepository;
        this.ecotopRepository = ecotopRepository;
        this.bookLevelRepository = bookLevelRepository;
        this.accountRepository = accountRepository;
        this.fieldRepository = fieldRepository;
    }

    private Page<CollectionDto> getCollectionDtos(Page<Seed> page, Boolean auth) {
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

            if (!auth) {
                Set<Field> hiddenFields = fieldRepository.findAllBySeedsContains(seed);
                Set<String> hidden = hiddenFields.stream()
                        .map(Field::getField)
                        .collect(Collectors.toSet());
                if (hidden.contains("id")) collectionDto.setId(null);
                if (hidden.contains("redBookRF")) collectionDto.setRedBookRF(null);
                if (hidden.contains("family")) collectionDto.setFamily(null);
                if (hidden.contains("genus")) collectionDto.setGenus(null);
                if (hidden.contains("specie")) collectionDto.setSpecie(null);
                if (hidden.contains("redList")) collectionDto.setRedList(null);
            }

            return collectionDto;
        });
    }

    private static boolean isAuthorize() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private void optimizeDB(Integer familyId, Integer genusId, Integer specieId, Integer redListId, Integer redBookRFId, Integer redBookSOId, Integer placeOfCollectionId, Integer ecotopId) {
        if (specieId != null && !specieRepository.existsAnySeed(specieId)) specieRepository.deleteById(specieId);
        if (genusId != null && !genusRepository.existsAnySpecie(genusId)) genusRepository.deleteById(genusId);
        if (familyId != null && !familyRepository.existsAnyGenus(familyId)) familyRepository.deleteById(familyId);
        if (redListId != null && !redListRepository.existsAnySeed(redListId)) redListRepository.deleteById(redListId);
        if (redBookRFId != null && !redBookRepository.existsAnySeedRF(redBookRFId)) redBookRepository.deleteById(redBookRFId);
        if (redBookSOId != null && !redBookRepository.existsAnySeedSO(redBookSOId)) redBookRepository.deleteById(redBookSOId);
        if (placeOfCollectionId != null && !placeOfCollectionRepository.existsAnySeed(placeOfCollectionId)) placeOfCollectionRepository.deleteById(placeOfCollectionId);
        if (ecotopId != null && !ecotopRepository.existsAnySeed(ecotopId)) ecotopRepository.deleteById(ecotopId);
    }

    public Page<CollectionDto> getAllCollectionSeeds(Pageable pageable) {
        boolean auth = isAuthorize();
        Page<Seed> page;
        if (auth) page = seedRepository.findAll(pageable);
        else page = seedRepository.findAllByIsHiddenIsFalseOrIsHiddenIsNull(pageable);
        return getCollectionDtos(page, auth);
    }

    public Page<CollectionDto> getAllCollectionSeedsByParams(String specie, String genus, String family, Pageable pageable) {
        boolean auth = isAuthorize();
        specie = specie == null ? null : "%" + specie + "%";
        genus = genus == null ? null : "%" + genus + "%";
        family = family == null ? null : "%" + family + "%";
        Page<Seed> page = seedRepository.findSeedsBySpecieAndGenusAndFamily(specie, genus, family, auth, pageable);
        return getCollectionDtos(page, auth);
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

        Set<Field> hiddenFields = fieldRepository.findAllBySeedsContains(seed);
        Set<String> hidden = hiddenFields.stream()
                .map(Field::getField)
                .collect(Collectors.toSet());
        FieldsDto fieldsDto = new FieldsDto();
        HashMap<String, Boolean> fields = fieldsDto.getFields();

        if (hidden.contains("id")) fields.put("id", true);
        if (hidden.contains("seedName")) fields.put("seedName", true);
        if (hidden.contains("family")) fields.put("family", true);
        if (hidden.contains("genus")) fields.put("genus", true);
        if (hidden.contains("specie")) fields.put("specie", true);
        if (hidden.contains("redList")) fields.put("redList", true);
        if (hidden.contains("redBookRF")) fields.put("redBookRF", true);
        if (hidden.contains("redBookSO")) fields.put("redBookSO", true);
        if (hidden.contains("dateOfCollection")) fields.put("dateOfCollection", true);
        if (hidden.contains("placeOfCollection")) fields.put("placeOfCollection", true);
        if (hidden.contains("weightOf1000Seeds")) fields.put("weightOf1000Seeds", true);
        if (hidden.contains("numberOfSeeds")) fields.put("numberOfSeeds", true);
        if (hidden.contains("completedSeeds")) fields.put("completedSeeds", true);
        if (hidden.contains("seedGermination")) fields.put("seedGermination", true);
        if (hidden.contains("seedMoisture")) fields.put("seedMoisture", true);
        if (hidden.contains("GPS")) fields.put("GPS", true);
        if (hidden.contains("ecotop")) fields.put("ecotop", true);
        if (hidden.contains("pestInfestation")) fields.put("pestInfestation", true);
        if (hidden.contains("comment")) fields.put("comment", true);
        if (hidden.contains("photoXRay")) fields.put("photoXRay", true);
        if (hidden.contains("photoSeed")) fields.put("photoSeed", true);
        if (hidden.contains("photoEcotop")) fields.put("photoEcotop", true);

        seedDto.setFields(fields);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                if (seed.getIsHidden() != null && seed.getIsHidden()) throw new ResourceNotFoundException();

                if (fields.get("id")) seedDto.setId(null);
                if (fields.get("seedName")) seedDto.setSeedName(null);
                if (fields.get("family")) seedDto.setFamily(null);
                if (fields.get("genus")) seedDto.setGenus(null);
                if (fields.get("specie")) seedDto.setSpecie(null);
                if (fields.get("redList")) seedDto.setRedList(null);
                if (fields.get("redBookRF")) seedDto.setRedBookRF(null);
                if (fields.get("redBookSO")) seedDto.setRedBookSO(null);
                if (fields.get("dateOfCollection")) seedDto.setDateOfCollection(null);
                if (fields.get("placeOfCollection")) seedDto.setPlaceOfCollection(null);
                if (fields.get("weightOf1000Seeds")) seedDto.setWeightOf1000Seeds(null);
                if (fields.get("numberOfSeeds")) seedDto.setNumberOfSeeds(null);
                if (fields.get("completedSeeds")) seedDto.setCompletedSeeds(null);
                if (fields.get("seedGermination")) seedDto.setSeedGermination(null);
                if (fields.get("seedMoisture")) seedDto.setSeedMoisture(null);
                if (fields.get("GPS")) {
                    seedDto.setGPSLatitude(null);
                    seedDto.setGPSLongitude(null);
                    seedDto.setGPSAltitude(null);
                }
                if (fields.get("ecotop")) seedDto.setEcotop(null);
                if (fields.get("pestInfestation")) seedDto.setPestInfestation(null);
                if (fields.get("comment")) seedDto.setComment(null);
            }

            seedDto.setIsHidden(seed.getIsHidden());

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SeedBankUserDetails userDetails = (SeedBankUserDetails) authentication.getPrincipal();
        seed.setSeedId(id);
        seed.setAccount(accountRepository.findAccountByAccountId(userDetails.getAccountId()));
        seedRepository.save(seed);
        return id;
    }

    public void editSeed(SeedDto dto) {
        Seed seed = seedRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Seed not found"));
        // Find or create Family
        Integer familyId = null;
        if (seed.getSpecie() != null && seed.getSpecie().getGenus() != null && seed.getSpecie().getGenus().getFamily() != null)
            familyId = familyRepository.findFamilyByNameOfFamily(seed.getSpecie().getGenus().getFamily().getNameOfFamily())
                    .map(Family::getFamilyId).orElse(null);
        Family family = familyRepository.findFamilyByNameOfFamily(dto.getFamily())
                .orElseGet(() -> {
                    Family newFamily = new Family();
                    newFamily.setNameOfFamily(dto.getFamily());
                    return familyRepository.save(newFamily);
                });
        // Find or create Genus
        Integer genusId = null;
        if (seed.getSpecie() != null && seed.getSpecie().getGenus() != null)
            genusId = genusRepository.findGenusByNameOfGenus(seed.getSpecie().getGenus().getNameOfGenus())
                    .map(Genus::getGenusId).orElse(null);
        Genus genus = genusRepository.findGenusByNameOfGenusAndFamily(dto.getGenus(), family)
                .orElseGet(() -> {
                    Genus newGenus = new Genus();
                    newGenus.setNameOfGenus(dto.getGenus());
                    newGenus.setFamily(family);
                    return genusRepository.save(newGenus);
                });
        // Find or create Specie
        Integer specieId = null;
        if (seed.getSpecie() != null)
            specieId = specieRepository.findSpecieByNameOfSpecie(seed.getSpecie().getNameOfSpecie())
                    .map(Specie::getSpecieId).orElse(null);
        Specie specie = specieRepository.findSpecieByNameOfSpecieAndGenus(dto.getSpecie(), genus)
                .orElseGet(() -> {
                    Specie newSpecie = new Specie();
                    newSpecie.setNameOfSpecie(dto.getSpecie());
                    newSpecie.setGenus(genus);
                    return specieRepository.save(newSpecie);
                });
        // Find or create RedList
        Integer redListId = null;
        if (seed.getRed_list() != null)
            redListId = redListRepository.findRedListByCategory(seed.getRed_list().getCategory())
                    .map(RedList::getCategoryId).orElse(null);
        RedList redList = redListRepository.findRedListByCategory(dto.getRedList())
                .orElseGet(() -> {
                    RedList newRedList = new RedList();
                    newRedList.setCategory(dto.getRedList());
                    return redListRepository.save(newRedList);
                });
        // Find or create RedBookRF, categoryId = 2
        Integer redBookRFId = null;
        BookLevel bookLevelRF = bookLevelRepository.findById(2).orElseThrow(() -> new RuntimeException("RF book level not found"));
        if (seed.getRed_book_rf() != null)
            redBookRFId = redBookRepository.findRedBookByCategoryAndBookLevel(seed.getRed_book_rf().getCategory(), bookLevelRF)
                    .map(RedBook::getCategoryId).orElse(null);
        RedBook redBookRF = redBookRepository.findRedBookByCategoryAndBookLevel(dto.getRedBookRF(), bookLevelRF)
                .orElseGet(() -> {
                    RedBook newRedBook = new RedBook();
                    newRedBook.setCategory(dto.getRedBookRF());
                    newRedBook.setBookLevel(bookLevelRF);
                    return redBookRepository.save(newRedBook);
                });
        // Find or create RedBookSO, categoryId = 1
        Integer redBookSOId = null;
        BookLevel bookLevelSO = bookLevelRepository.findById(1).orElseThrow(() -> new RuntimeException("SO book level not found"));
        if (seed.getRed_book_so() != null)
            redBookSOId = redBookRepository.findRedBookByCategoryAndBookLevel(seed.getRed_book_so().getCategory(), bookLevelSO)
                    .map(RedBook::getCategoryId).orElse(null);
        RedBook redBookSO = redBookRepository.findRedBookByCategoryAndBookLevel(dto.getRedBookSO(), bookLevelSO)
                .orElseGet(() -> {
                    RedBook newRedBook = new RedBook();
                    newRedBook.setCategory(dto.getRedBookSO());
                    newRedBook.setBookLevel(bookLevelSO);
                    return redBookRepository.save(newRedBook);
                });
        // Find or create PlaceOfColelction
        Integer placeOfCollectionId = null;
        if (seed.getPlace_of_collection() != null)
            placeOfCollectionId = placeOfCollectionRepository.findPlaceOfCollectionByPlaceOfCollection(seed.getPlace_of_collection().getPlaceOfCollection())
                    .map(PlaceOfCollection::getPlaceOfCollectionId).orElse(null);
        PlaceOfCollection placeOfCollection = placeOfCollectionRepository.findPlaceOfCollectionByPlaceOfCollection(dto.getPlaceOfCollection())
                .orElseGet(() -> {
                    PlaceOfCollection newPlaceOfCollection = new PlaceOfCollection();
                    newPlaceOfCollection.setPlaceOfCollection(dto.getPlaceOfCollection());
                    return placeOfCollectionRepository.save(newPlaceOfCollection);
                });
        // Find or create Ecotop
        Integer ecotopId = null;
        if (seed.getSpecie() != null)
            ecotopId = ecotopRepository.findEcotopByNameOfEcotop(seed.getEcotop().getNameOfEcotop())
                    .map(Ecotop::getEcotopId).orElse(null);
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

        seed.getFields().clear();

        // Обновление с новыми значениями
        dto.getFields().forEach((fieldKey, value) -> {
            if (value != null) {
                Field field = fieldRepository.findByField(fieldKey);
                seed.getFields().add(field);
            }
        });

        seed.setIsHidden(dto.getIsHidden() != null && dto.getIsHidden());

        seedRepository.save(seed);

        // Оптимизация БД
        optimizeDB(familyId, genusId, specieId, redListId, redBookRFId, redBookSOId, placeOfCollectionId, ecotopId);
    }

    public void deleteSeed(String id) {
        Seed seed = seedRepository.findById(id).orElseThrow(() -> new RuntimeException("Seed not found"));
        // Find Family
        Integer familyId = null;
        if (seed.getSpecie() != null && seed.getSpecie().getGenus() != null && seed.getSpecie().getGenus().getFamily() != null)
            familyId = familyRepository.findFamilyByNameOfFamily(seed.getSpecie().getGenus().getFamily().getNameOfFamily())
                    .map(Family::getFamilyId).orElse(null);
        // Find Genus
        Integer genusId = null;
        if (seed.getSpecie() != null && seed.getSpecie().getGenus() != null)
            genusId = genusRepository.findGenusByNameOfGenus(seed.getSpecie().getGenus().getNameOfGenus())
                    .map(Genus::getGenusId).orElse(null);
        // Find Specie
        Integer specieId = null;
        if (seed.getSpecie() != null)
            specieId = specieRepository.findSpecieByNameOfSpecie(seed.getSpecie().getNameOfSpecie())
                    .map(Specie::getSpecieId).orElse(null);
        // Find RedList
        Integer redListId = null;
        if (seed.getRed_list() != null)
            redListId = redListRepository.findRedListByCategory(seed.getRed_list().getCategory())
                    .map(RedList::getCategoryId).orElse(null);
        // Find RedBookRF, categoryId = 2
        Integer redBookRFId = null;
        BookLevel bookLevelRF = bookLevelRepository.findById(2).orElseThrow(() -> new RuntimeException("RF book level not found"));
        if (seed.getRed_book_rf() != null)
            redBookRFId = redBookRepository.findRedBookByCategoryAndBookLevel(seed.getRed_book_rf().getCategory(), bookLevelRF)
                    .map(RedBook::getCategoryId).orElse(null);
        // Find RedBookSO, categoryId = 1
        Integer redBookSOId = null;
        BookLevel bookLevelSO = bookLevelRepository.findById(1).orElseThrow(() -> new RuntimeException("SO book level not found"));
        if (seed.getRed_book_so() != null)
            redBookSOId = redBookRepository.findRedBookByCategoryAndBookLevel(seed.getRed_book_so().getCategory(), bookLevelSO)
                    .map(RedBook::getCategoryId).orElse(null);
        // Find PlaceOfColelction
        Integer placeOfCollectionId = null;
        if (seed.getPlace_of_collection() != null)
            placeOfCollectionId = placeOfCollectionRepository.findPlaceOfCollectionByPlaceOfCollection(seed.getPlace_of_collection().getPlaceOfCollection())
                    .map(PlaceOfCollection::getPlaceOfCollectionId).orElse(null);
        // Find Ecotop
        Integer ecotopId = null;
        if (seed.getSpecie() != null)
            ecotopId = ecotopRepository.findEcotopByNameOfEcotop(seed.getEcotop().getNameOfEcotop())
                    .map(Ecotop::getEcotopId).orElse(null);

        seed.getFields().clear();

        seedRepository.deleteById(id);

        // Оптимизация БД
        optimizeDB(familyId, genusId, specieId, redListId, redBookRFId, redBookSOId, placeOfCollectionId, ecotopId);
    }

}
