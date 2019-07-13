package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
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

    public void transformVideo(FFmpegExecutor executor, FFmpegBuilder builder, HashMap<String, Long> mediaInfo, String inputFile, String outputFile){

        long maxrate = mediaInfo.get("video.bitrate")*Config.getInstance().getSettingsAppMaxrateFactor();
        long bufsize = mediaInfo.get("video.bitrate")*Config.getInstance().getSettingsAppBufsizeFactor();
        long keyint  = mediaInfo.get("video.fps")*Config.getInstance().getSettingsAppSegmentSize();

        builder.setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setVideoCodec("libx264")
                .addExtraArgs("-preset", "slow")
                .setVideoFrameRate(mediaInfo.get("video.fps"))//FPS
                .setVideoBitRate(mediaInfo.get("video.bitrate"))
                .addExtraArgs("-maxrate", String.valueOf(maxrate))
                .addExtraArgs("-bufsize", String.valueOf(bufsize))
                .addExtraArgs("-keyint", String.valueOf(keyint))
                .addExtraArgs("-min-keyint", String.valueOf(keyint))
                .addExtraArgs("-scenecut", "0")
                .addExtraArgs("-no-scenecut", "0")
                .addExtraArgs("-pass", "1")
                .setVideoResolution(mediaInfo.get("video.width").intValue(), mediaInfo.get("video.height").intValue())
                //.addExtraArgs()
                .done();
        try {
            executor.createJob(builder, new ProgressLogger(inputFile) ).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
