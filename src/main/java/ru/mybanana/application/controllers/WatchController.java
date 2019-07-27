package ru.mybanana.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class WatchController {
    @GetMapping("/watch/")
    public String watchPage(Model model) throws IOException {
        return "watch";
    }
}
