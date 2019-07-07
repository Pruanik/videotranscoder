package ru.mybanana.application;

import ru.mybanana.media.VideoTranscoder;

import java.io.IOException;

public class App {

    public static void main(String[] args){
        VideoTranscoder transcoder = new VideoTranscoder("in/input.avi");
        try {
            transcoder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
