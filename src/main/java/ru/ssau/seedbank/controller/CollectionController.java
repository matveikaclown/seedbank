package ru.ssau.seedbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

@Controller
public class CollectionController {

    private final SeedService seedService;
    private final PhotoService photoService;

    @Autowired
    public CollectionController(SeedService seedService, PhotoService photoService) {
        this.seedService = seedService;
        this.photoService = photoService;
    }

    @GetMapping("/collection")
    public String allSeeds(
            @PageableDefault(value = 20, sort = {"seedId"}) Pageable pageable,
            @RequestParam(value = "family", required = false) String family,
            @RequestParam(value = "genus", required = false) String genus,
            @RequestParam(value = "specie", required = false) String specie,
            Model model) {
        if (family != null && !family.isEmpty() || genus != null && !genus.isEmpty() || specie != null && !specie.isEmpty()) {
            model.addAttribute("seeds", seedService.getAllSeedsByParams(specie, genus, family, pageable));
        }
        else {
            model.addAttribute("seeds", seedService.getAllSeeds(pageable));
        }
        model.addAttribute("_family", family);
        model.addAttribute("_genus", genus);
        model.addAttribute("_specie", specie);
        return "collection";
    }

    @GetMapping("/collection/id={id}")
    public String seed(
            @PathVariable("id") Integer id,
            Model model) {
        Seed seed = seedService.getSeedById(id);
        String _xray = photoService.encodeBase64("images\\" + seed.getSeedId().toString() + "\\xray.jpg");
        String _seed = photoService.encodeBase64("images\\" + seed.getSeedId().toString() + "\\seed.jpg");
        String _ecotop = photoService.encodeBase64("images\\" + seed.getSeedId().toString() + "\\ecotop.jpg");
        model.addAttribute("seed", seed);
        model.addAttribute("xray", _xray);
        model.addAttribute("seedPh", _seed);
        model.addAttribute("ecotop", _ecotop);
        return "seed";
    }

}
