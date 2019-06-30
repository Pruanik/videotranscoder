package ru.mybanana;

import net.bramp.ffmpeg.FFmpeg;

import java.io.IOException;
import java.io.File;

public class VideoTranscoder {

    public static void main(String[] args) throws IOException {
        System.out.println("HI, WELCOME!");

        try{
            InitFFMPEG();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void InitFFMPEG() throws IOException {
        File f = new File("/usr/bin/ffmpeg");
        FFmpeg ffmpeg = new FFmpeg(f.getAbsolutePath());
        System.out.println(ffmpeg.version());
    }
}
