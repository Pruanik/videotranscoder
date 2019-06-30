package ru.mybanana;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;
import java.io.File;

public class VideoTranscoder {

    private static FFmpeg ffmpeg;
    private static String InputFilePath = "in/input.mp4";

    public static void main(String[] args) throws IOException {
        System.out.println("Start processing..!");

        File InputFile = new File(InputFilePath);

        try{
            InitFFMPEG();
            File VideoOut = TransformVideo(InputFile);
            File AudioOut = TransformAudio(InputFile);
            File Mp4Packeg = PackingToMp4(VideoOut, AudioOut);
            Mp4ToDASH(Mp4Packeg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void InitFFMPEG() throws IOException {
        File f = new File("/usr/bin/ffmpeg");
        ffmpeg = new FFmpeg(f.getAbsolutePath());
        System.out.println(ffmpeg.version());
    }

    private static File TransformVideo(File file) throws IOException{

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput("out/output.mp3")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder, progress -> {
            if(progress.isEnd()){
                System.out.println("Transcoder finished task");
            }
        }).run();

        return new File("out/output.264");
    }

    private static File TransformAudio(File file) throws IOException{
        //..
        return new File("out/output.aac");
    }

    private static File PackingToMp4(File VideoThrow, File AudioThrow){
        //..
        return new File("out/output.mp4");
    }

    private static void Mp4ToDASH(File Mp4Packeg){
        System.out.println("Manifest complete");
    }
}
