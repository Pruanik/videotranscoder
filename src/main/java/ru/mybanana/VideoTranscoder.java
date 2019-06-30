package ru.mybanana;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.io.IOException;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class VideoTranscoder {

    private static FFmpeg ffmpeg;
    private static FFprobe ffprobe;
    private static FFmpegExecutor executor;
    private static String InputFilePath = "in/input.mp4";
    private static FFmpegProbeResult InputFileProbeResult;

    public static void main(String[] args) throws IOException {
        System.out.println("Start processing..!");

        File InputFile = new File(InputFilePath);

        try{
            InitFFMPEG();

            InputFileProbeResult = ffprobe.probe(InputFile.getAbsolutePath());

            //File VideoOut = TransformVideo(InputFile);
            File AudioOut = TransformAudio(InputFile);
            //File Mp4Packeg = PackingToMp4(VideoOut, AudioOut);
            //Mp4ToDASH(Mp4Packeg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void InitFFMPEG() throws IOException {
        ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        ffprobe = new FFprobe("/usr/bin/ffprobe");
        executor = new FFmpegExecutor(ffmpeg, ffprobe);
        System.out.println(ffmpeg.version());
    }

    private static File TransformVideo(File file) throws IOException{

        String OutputFile = "out/video_throw.264";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(OutputFile)
                    .setVideoCodec("libx264")
                    .setVideoBitRate(2000)
                    .setVideoFrameRate(24)//FPS
                    .setVideoResolution(1280, 720)
                //.addExtraArgs()
                .done();

        executor.createJob(builder, new ProgressListener() {

            // Using the FFmpegProbeResult determine the duration of the input
            final double duration_ns = InputFileProbeResult.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                // Print out interesting information about the progress
                System.out.println(String.format(
                        "[%.0f%%] status:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
                        percentage * 100,
                        progress.status,
                        progress.frame,
                        FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                        progress.fps.doubleValue(),
                        progress.speed
                ));
            }
        }).run();

        return new File(OutputFile);
    }

    private static File TransformAudio(File file) throws IOException{

        String OutputFile = "out/output.aac";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(file.getAbsolutePath())
                .overrideOutputFiles(true)
                .addOutput(OutputFile)
                .setAudioCodec("aac")
                .done();

        executor.createJob(builder, new ProgressListener() {

            // Using the FFmpegProbeResult determine the duration of the input
            final double duration_ns = InputFileProbeResult.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                // Print out interesting information about the progress
                System.out.println(String.format(
                        "[%.0f%%] status:%s frame:%d time:%s ms fps:%.0f speed:%.2fx",
                        percentage * 100,
                        progress.status,
                        progress.frame,
                        FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS),
                        progress.fps.doubleValue(),
                        progress.speed
                ));
            }
        }).run();

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
