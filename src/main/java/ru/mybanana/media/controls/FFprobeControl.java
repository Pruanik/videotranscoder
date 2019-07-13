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

    public HashMap<String, Long> getMediaInfo(File file){

        HashMap<String, Long> hashMap = new HashMap<>();

        try {

            FFmpegProbeResult probeResult = ffprobe.probe(file.getAbsolutePath());
            FFmpegStream video_stream = probeResult.getStreams().get(0);
            FFmpegStream audio_stream = probeResult.getStreams().get(1);

            hashMap.put("video.bitrate", video_stream.bit_rate);
            hashMap.put("video.fps", video_stream.avg_frame_rate.longValue());
            hashMap.put("audio.bitrate", audio_stream.bit_rate);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}
