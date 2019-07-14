package ru.mybanana.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.mybanana.application.service.StorageService;
import ru.mybanana.media.VideoTranscoder;

import java.io.IOException;

@SpringBootApplication
public class App {

    public static void main(String[] args){
//        VideoTranscoder transcoder = new VideoTranscoder("in/input.avi");
//        try {
//            transcoder.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
