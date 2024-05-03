package ru.ssau.seedbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ssau.seedbank.dto.SeedDto;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

@Controller
@RequestMapping("/collection")
public class CollectionController {

    private final SeedService seedService;
    private final PhotoService photoService;

    @Autowired
    public CollectionController(SeedService seedService, PhotoService photoService) {
        this.seedService = seedService;
        this.photoService = photoService;
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
            @PathVariable("id") Integer id,
            Model model) {
        SeedDto seed = seedService.getSeedById(id);
        String _xray = photoService.encodeBase64("images\\" + seed.getId().toString() + "\\xray.jpg");
        String _seed = photoService.encodeBase64("images\\" + seed.getId().toString() + "\\seed.jpg");
        String _ecotop = photoService.encodeBase64("images\\" + seed.getId().toString() + "\\ecotop.jpg");
        model.addAttribute("seed", seed);
        model.addAttribute("xray", _xray);
        model.addAttribute("seedPh", _seed);
        model.addAttribute("ecotop", _ecotop);
        return "seed";
    }

    @GetMapping("/add")
    public String newSeed(@RequestParam(value = "id", required = false) Integer id, Model model) {
        model.addAttribute("id", id);
        return "newSeed";
    }

    @PostMapping("/add")
    public String addSeed(@RequestParam(value = "id") Integer id) {
        seedService.addNewSeed(id);
        return "redirect:/collection/id=" + id; /*TODO сделать норм перенаправление*/
    }

}
