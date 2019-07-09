package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import ru.mybanana.tools.Config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FFprobeControl {

    private FFprobe ffprobe;

    public FFprobeControl() throws IOException {
        ffprobe = new FFprobe(Config.getInstance().getFFprobeLocation());
    }

    public HashMap<String, String> getMediaInfo(File file){
        try {
            FFmpegProbeResult probeResult = ffprobe.probe(file.getAbsolutePath());
            FFmpegStream stream = probeResult.getStreams().get(0);
            //System.out.println(stream.codec_long_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
