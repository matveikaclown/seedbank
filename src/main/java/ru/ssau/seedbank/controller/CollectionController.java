package ru.ssau.seedbank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.service.CsvService;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    private final SeedService seedService;
    private final PhotoService photoService;
    private final CsvService csvService;

    @Autowired
    public CollectionController(SeedService seedService, PhotoService photoService, CsvService csvService) {
        this.seedService = seedService;
        this.photoService = photoService;
        this.csvService = csvService;
    }

    @GetMapping
    public String allSeeds(
            @PageableDefault(value = 20, sort = {"seedId"}) Pageable pageable,
            @RequestParam(value = "family", required = false) String family,
            @RequestParam(value = "genus", required = false) String genus,
            @RequestParam(value = "specie", required = false) String specie,
            Model model) {
        if (family != null && !family.isEmpty() || genus != null && !genus.isEmpty() || specie != null && !specie.isEmpty()) {
            model.addAttribute("seeds", seedService.getAllCollectionSeedsByParams(specie, genus, family, pageable));
        }
        else {
            model.addAttribute("seeds", seedService.getAllCollectionSeeds(pageable));
        }
        model.addAttribute("_family", family);
        model.addAttribute("_genus", genus);
        model.addAttribute("_specie", specie);
        return "collection";
    }

    @GetMapping("/id={id}")
    public String seed(
            @PathVariable("id") String id,
            Model model) {
        SeedDto seed = seedService.getSeedById(id);
        String _xray = photoService.encodeBase64("images\\" + seed.getId() + "\\xray", "xray", id);
        String _seed = photoService.encodeBase64("images\\" + seed.getId() + "\\seed", "seed", id);
        String _ecotop = photoService.encodeBase64("images\\" + seed.getId() + "\\ecotop", "ecotop", id);
        model.addAttribute("seed", seed);
        model.addAttribute("xray", _xray);
        model.addAttribute("seedPh", _seed);
        model.addAttribute("ecotop", _ecotop);
        return "seed";
    }

    @GetMapping("/add")
    public String newSeed(String id, Model model) {
        model.addAttribute("id", id);
        return "newSeed";
    }

    @PostMapping("/add")
    public String addSeed(
            @RequestParam(value = "taxon") String taxon,
            @RequestParam(value = "place") String place,
            @RequestParam(value = "year") String year,
            @RequestParam(value = "delectus") String delectus) {
        String id = taxon + "-" + place + "-" + year + "-" + delectus;
        id = seedService.addNewSeed(id);
        return "redirect:/collection/edit/id=" + id;
    }

    @GetMapping("/edit/id={id}")
    public String editSeed(
            @PathVariable(value = "id") String id,
            Model model
            ) {
        SeedDto seedDto = seedService.getSeedById(id);
        String xRay = photoService.encodeBase64("images\\" + seedDto.getId() + "\\xray", "", "");
        String seed = photoService.encodeBase64("images\\" + seedDto.getId() + "\\seed", "", "");
        String ecotopPhoto = photoService.encodeBase64("images\\" + seedDto.getId() + "\\ecotop", "", "");
        model.addAttribute("seedDto", seedDto);
        model.addAttribute("id", id);
        model.addAttribute("xRay", xRay);
        model.addAttribute("seed", seed);
        model.addAttribute("ecotopPhoto", ecotopPhoto);
        return "editSeed";
    }

    @PostMapping("/edit/id={id}")
    public String saveSeed(
            @PathVariable(value = "id") String id,
            @Valid @ModelAttribute(value = "seedDto") SeedDto seedDto,
            BindingResult bindingResult,
            @RequestParam(value = "action") String action,
            @RequestParam(value = "xRay", required = false) MultipartFile xRay,
            @RequestParam(value = "seed", required = false) MultipartFile seed,
            @RequestParam(value = "ecotopPhoto", required = false) MultipartFile ecotopPhoto,
            @RequestParam(value = "deleteXRay", required = false) Boolean deleteXRay,
            @RequestParam(value = "deleteSeed", required = false) Boolean deleteSeed,
            @RequestParam(value = "deleteEcotop", required = false) Boolean deleteEcotop
    ) {
        if ("save".equals(action)) {
            seedService.editSeed(seedDto);
            photoService.savePhoto(xRay, id, "xray", deleteXRay);
            photoService.savePhoto(seed, id, "seed", deleteSeed);
            photoService.savePhoto(ecotopPhoto, id, "ecotop", deleteEcotop);
            return "redirect:/collection/id=" + id;
        } else {
            photoService.deletePhotos(id);
            seedService.deleteSeed(id);
            return "redirect:/collection";
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<byte[]> getAll() {
        return csvService.exportAllCsv();
    }

    @GetMapping("/get/id={id}")
    public ResponseEntity<byte[]> getSeed(@PathVariable(value = "id") String id) {
        return csvService.exportSeedCsv(id);
    }

}
