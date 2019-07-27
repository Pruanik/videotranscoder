package ru.mybanana.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.mybanana.application.models.Videos;
import ru.mybanana.application.repository.VideoRepository;
import ru.mybanana.tools.dirCreator;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @Autowired
    private VideoRepository VideoRepository;

    @Value("${storage.video.path}")
    private String storageVideoPath;

    @Value("${storage.image.path}")
    private String storageImagePath;

    @GetMapping("/upload/")
    public String uploadPage(Model model) throws IOException {
        return "upload";
    }

    @PostMapping("/upload/")
    public String handleFileUpload(
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam("preview") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            Model model) throws IOException {

        if( videoFile == null || videoFile.isEmpty()){
            model.addAttribute("message", "Video File is Empty!");
            return "upload";
        }

        dirCreator.create(storageVideoPath);
        dirCreator.create(storageImagePath);

        String videoName = UUID.randomUUID().toString();
        String imageName = UUID.randomUUID().toString();

        videoFile.transferTo(new File(storageVideoPath + videoName));
        imageFile.transferTo(new File(storageVideoPath + imageName));
        //Save video  file
        //Transcode video  file
        //Save image file
        //Get information about video
        //Save to database



        //Videos videos = new Videos(nameVideo, "blabla.com", (float) 15.02, "kek.ru", descriptionVideo);
        //VideoRepository.save(videos);

        //VideoTranscoder transcoder = new VideoTranscoder(storageService.getName());
        //try {
        //    transcoder.start();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //redirectAttributes.addFlashAttribute("message", "Uploaded "+file.getOriginalFilename()+" - seccess");
        //model.addAttribute("message", name);
        //Video video = new Video();
        //video.setName(file.getOriginalFilename());
        //VideoRepository.save(video);

        model.addAttribute("message", "SUCCESS!");
        return "upload";
    }
}
