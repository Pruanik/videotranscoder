package ru.mybanana.media;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import ru.mybanana.tools.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class VideoTranscoder {

    private static FFmpeg ffmpeg;
    private static FFprobe ffprobe;
    private static FFmpegExecutor executor;
    private static FFmpegProbeResult InputFileProbeResult;
    private String InputFilePath;

    public VideoTranscoder(String InputFilePath){
        this.InputFilePath = InputFilePath;
    }

    public void start() throws IOException {
        Config config = new Config();
        System.out.println(config.getFFmpegLocation());
        /*System.out.println("Start processing file "+InputFilePath);

        File InputFile = new File(InputFilePath);

        try{
            InitFFMPEG();

            InputFileProbeResult = ffprobe.probe(InputFile.getAbsolutePath());

            File VideoOut = TransformVideo(InputFile);
            File AudioOut = TransformAudio(InputFile);
            File Mp4VideoPackeg = PackingToMp4(VideoOut, "video");
            File Mp4AudioPackeg = PackingToMp4(AudioOut, "audio");
            Mp4ToDASH(Mp4VideoPackeg, Mp4AudioPackeg);
        } catch (IOException e){
            e.printStackTrace();
        }*/
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

    private static File PackingToMp4(File ContentThrow, String type){
        String OutputFile = "out/out"+type+".mp4";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "MP4Box -add "+ContentThrow.getAbsolutePath()+" "+OutputFile);
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentThrow.delete();

        return new File(OutputFile);
    }

    private static void Mp4ToDASH(File Mp4VideoContainer,File Mp4AudioContainer){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "MP4Box -dash 4000 -frag 4000 -rap -segment-name %s/seg_ -url-template -out out/final.mpd "+Mp4VideoContainer.getAbsolutePath()+" "+Mp4AudioContainer.getAbsolutePath());
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Manifest complete");
                System.out.println(output);
                Mp4VideoContainer.delete();
                Mp4AudioContainer.delete();
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}