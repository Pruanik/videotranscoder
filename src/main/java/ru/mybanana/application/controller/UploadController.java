package ru.mybanana.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UploadController {

    @GetMapping("/upload/")
    public String home(@RequestParam(name="file", required = false, defaultValue = "") String file, Model model){
        model.addAttribute("file", file);
        return "upload";
    }

}
