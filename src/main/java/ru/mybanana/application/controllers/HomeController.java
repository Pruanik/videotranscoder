package ru.mybanana.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(name="message", required = false, defaultValue = "") String name, Model model){
        model.addAttribute("message", name);
        return "home";
    }

}
