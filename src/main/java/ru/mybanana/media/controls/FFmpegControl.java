package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import ru.mybanana.media.VideoMediaInfo;
import ru.mybanana.tools.Config;
import ru.mybanana.tools.ProgressLogger;

import java.io.IOException;
import java.util.HashMap;

public class FFmpegControl {

    private FFmpeg ffmpeg;

    public FFmpegControl() {
        try {
            ffmpeg = new FFmpeg(Config.getInstance().getFFmpegLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showVersion(){
        try {
            System.out.println(ffmpeg.version());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FFmpeg getObject(){
        return ffmpeg;
    }

    public void transformVideo(FFmpegExecutor executor, FFmpegBuilder builder, VideoMediaInfo mediaInfo, String inputFile, String outputFile){

        long maxrate = mediaInfo.getVideoBitrate()*Config.getInstance().getSettingsAppMaxrateFactor();
        long bufsize = mediaInfo.getVideoBitrate()*Config.getInstance().getSettingsAppBufsizeFactor();
        long keyint  = mediaInfo.getVideoFps()*Config.getInstance().getSettingsAppSegmentSize();

        builder.setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setVideoCodec("libx264")
                .addExtraArgs("-preset", "slow")
                .setVideoFrameRate(mediaInfo.getVideoFps())//FPS
                .setVideoBitRate(mediaInfo.getVideoBitrate())
                .addExtraArgs("-maxrate", String.valueOf(maxrate))
                .addExtraArgs("-bufsize", String.valueOf(bufsize))
                .addExtraArgs("-x264opts", "keyint="+String.valueOf(keyint)+":min-keyint="+String.valueOf(keyint)+":scenecut=0:no-scenecut")
                .addExtraArgs("-pass", "1")
                .setVideoResolution(mediaInfo.getVideoWidth(), mediaInfo.getVideoHeight())
                .done();
        try {
            executor.createJob(builder, new ProgressLogger(inputFile) ).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transformAudio(FFmpegExecutor executor, FFmpegBuilder builder, VideoMediaInfo mediaInfo, String inputFile, String outputFile){

        builder.setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setAudioCodec("aac")
                .setAudioBitRate(mediaInfo.getAudioBitrate())
                .done();

        try {
            executor.createJob(builder, new ProgressLogger(inputFile) ).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
