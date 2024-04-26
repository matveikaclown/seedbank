package ru.ssau.seedbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ssau.seedbank.model.Seed;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

@Controller
public class AtlasController {

    private final SeedService seedService;
    private final PhotoService photoService;

    @Autowired
    public AtlasController(SeedService seedService, PhotoService photoService) {
        this.seedService = seedService;
        this.photoService = photoService;
    }

    @GetMapping("/atlas")
    public String atlas(@PageableDefault(value = 20, sort = /*{"seedName"}*/ {"seedId"}) Pageable pageable, Model model) {
        Page<Seed> seeds = seedService.getAllSeeds(pageable);
        model.addAttribute("seeds", seeds);
        model.addAttribute("seedPhotos", photoService.getAllPhotos(seeds));
        return "atlas";
    }

}