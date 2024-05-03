package ru.ssau.seedbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ssau.seedbank.dto.AtlasDto;
import ru.ssau.seedbank.service.PhotoService;
import ru.ssau.seedbank.service.SeedService;

@Controller
@RequestMapping("/atlas")
public class AtlasController {

    private final SeedService seedService;
    private final PhotoService photoService;

    @Autowired
    public AtlasController(SeedService seedService, PhotoService photoService) {
        this.seedService = seedService;
        this.photoService = photoService;
    }

    @GetMapping
    public String atlas(@PageableDefault(value = 20, sort = /*{"seedName"}*/ {"seedId"}) Pageable pageable, Model model) {
        Page<AtlasDto> seeds = seedService.getAllAtlasSeeds(pageable);
        model.addAttribute("seeds", seeds);
        model.addAttribute("seedPhotos", photoService.getAllPhotos(seeds));
        return "atlas";
    }

}
