package ru.ssau.seedbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlesController {

    @GetMapping("/articles")
    public String articles() {
        return "articles";
    }

}
