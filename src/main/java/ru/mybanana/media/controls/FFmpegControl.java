package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFmpeg;
import ru.mybanana.tools.Config;

import java.io.IOException;

public class FFmpegControl {

    private static FFmpeg ffmpeg;

    public FFmpegControl() throws IOException {
        ffmpeg = new FFmpeg(Config.getInstance().getFFmpegLocation());
    }

    public static void showVersion(){
        try {
            System.out.println(ffmpeg.version());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
