package ru.mybanana.media;

import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import ru.mybanana.media.controls.FFmpegControl;
import ru.mybanana.media.controls.FFprobeControl;
import ru.mybanana.tools.Randomizer;

import java.io.File;
import java.util.HashMap;

public class Video {

    private File inputFile;
    private HashMap<String, Long> mediaInfo;
    private String nameVideo264Throw;
    private String nameAudioAccThrow;
    private String nameMP4Package;

    private FFmpegControl FFmpeg;
    private FFprobeControl FFprobe;
    private FFmpegBuilder builder;
    private FFmpegExecutor executor;


    public Video(String InputFilePath){

        inputFile = new File(InputFilePath);
        try {
            mediaInfo = FFprobe.getMediaInfo(inputFile.getAbsolutePath());
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        FFmpeg = new FFmpegControl();
        FFprobe = new FFprobeControl();
        builder = new FFmpegBuilder();
        executor = new FFmpegExecutor(FFmpeg.getObject(), FFprobe.getObject());

        nameVideo264Throw = "out/"+Randomizer.getRandomString(15);
        nameAudioAccThrow = "out/"+Randomizer.getRandomString(15);
        nameMP4Package = "out/"+Randomizer.getRandomString(15);
    }

    public Video transformVideo(){

        FFmpeg.transformVideo(executor, builder, mediaInfo, inputFile.getAbsolutePath(), nameVideo264Throw);

        return this;
    }

    public Video transformAudio(){

        return this;
    }

    public Video packToMP4(){

        return this;
    }

    public String getDASH(){

        return "";
    }
}
