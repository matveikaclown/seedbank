package ru.ssau.seedbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/atlas")
public class AtlasController {

    @GetMapping
    public String atlas() {
        return "atlas";
    }

}
