package ru.mybanana.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mybanana.application.interfaces.FileStorage;
import ru.mybanana.application.models.Video;
import ru.mybanana.application.repository.VideoRepository;
import ru.mybanana.application.services.SystemFileStorage;
import ru.mybanana.media.VideoTranscoder;

import java.io.IOException;

@Controller
public class UploadController {

    private final FileStorage storageService;

    @Autowired
    public UploadController(SystemFileStorage storageService){
        this.storageService = storageService;
    }

    @GetMapping("/upload/")
    public String uploadForm(Model model) throws IOException {
        //model.addAttribute("file", file);
        return "upload";
    }

    @PostMapping("/upload/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        storageService.store(file);
        VideoTranscoder transcoder = new VideoTranscoder(storageService.getName());
        try {
            transcoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message", "Uploaded "+file.getOriginalFilename()+" - seccess");
        //model.addAttribute("message", name);
        Video video = new Video();
        video.setName(file.getOriginalFilename());
        //VideoRepository.save(video);

        //return "redirect:/";
        return "upload";
    }
}
