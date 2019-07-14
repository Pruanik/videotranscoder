package ru.mybanana.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

}
