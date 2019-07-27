package ru.mybanana.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SearchController {

    @GetMapping("/search/")
    public String searchPage(Model model) throws IOException {
        return "search";
    }

}
