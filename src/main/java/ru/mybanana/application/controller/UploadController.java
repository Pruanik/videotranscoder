package ru.mybanana.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mybanana.application.service.StorageService;

@Controller
public class UploadController {

    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService){
        this.storageService = storageService;
    }

    @GetMapping("/upload/")
    public String uploadForm(@RequestParam(name="file", required = false, defaultValue = "") String file, Model model){
        model.addAttribute("file", file);
        return "upload";
    }

    @PostMapping("/upload/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);

        redirectAttributes.addFlashAttribute("message", "Uploaded "+file.getOriginalFilename()+" - seccess");
        return "redirect:/";
    }

}
