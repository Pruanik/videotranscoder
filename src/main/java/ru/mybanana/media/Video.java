package ru.mybanana.media;

import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import ru.mybanana.media.controls.CmdExecutorControl;
import ru.mybanana.media.controls.FFmpegControl;
import ru.mybanana.media.controls.FFprobeControl;
import ru.mybanana.tools.Randomizer;

import java.io.File;

public class Video {

    private File inputFile;
    private VideoMediaInfo mediaInfo;
    private String nameVideo264Stream;
    private String nameAudioAccStream;
    private String nameVideoMp4Container;
    private String nameAudioMp4Container;
    private String nameMpdFile;

    private FFmpegControl FFmpeg;
    private FFprobeControl FFprobe;
    private FFmpegBuilder builder;
    private FFmpegExecutor executor;


    public Video(String InputFilePath){

        FFmpeg = new FFmpegControl();
        FFprobe = new FFprobeControl();
        builder = new FFmpegBuilder();
        executor = new FFmpegExecutor(FFmpeg.getObject(), FFprobe.getObject());

        inputFile = new File(InputFilePath);
        mediaInfo = new VideoMediaInfo(FFprobe.getMediaInfo(inputFile.getAbsolutePath()));

        nameVideo264Stream = "out/"+Randomizer.getRandomString(15)+".264";
        nameAudioAccStream = "out/"+Randomizer.getRandomString(15)+".aac";
        nameVideoMp4Container = "out/"+Randomizer.getRandomString(15)+".mp4";
        nameAudioMp4Container = "out/"+Randomizer.getRandomString(15)+".mp4";
        nameMpdFile = "out/"+Randomizer.getRandomString(15)+"_final.mpd";
    }

    public Video transformVideo(){

        FFmpeg.transformVideo(executor, builder, mediaInfo, inputFile.getAbsolutePath(), nameVideo264Stream);

        return this;
    }

    public Video transformAudio(){

        FFmpeg.transformAudio(executor, builder, mediaInfo, inputFile.getAbsolutePath(), nameAudioAccStream);

        return this;
    }

    public Video packToMP4(){

        CmdExecutorControl.packToMP4(nameVideo264Stream, nameVideoMp4Container);
        CmdExecutorControl.packToMP4(nameAudioAccStream, nameAudioMp4Container);

        return this;
    }

    public Video makeDASH(){

        CmdExecutorControl.Mp4ToDASH(nameVideoMp4Container, nameAudioMp4Container, nameMpdFile);

        return this;
    }

    public void cleanTmpFiles(){
        File file = new File(nameVideo264Stream);
        file.delete();

        file = new File(nameAudioAccStream);
        file.delete();

        file = new File(nameVideoMp4Container);
        file.delete();

        file = new File(nameAudioMp4Container);
        file.delete();
    }
}
