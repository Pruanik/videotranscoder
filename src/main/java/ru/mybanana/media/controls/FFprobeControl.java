package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import ru.mybanana.tools.Config;

import java.io.IOException;
import java.util.HashMap;

public class FFprobeControl {

    private FFprobe ffprobe;

    public FFprobeControl() {
        try {
            ffprobe = new FFprobe(Config.getInstance().getFFprobeLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FFprobe getObject(){
        return ffprobe;
    }

    public HashMap<String, Long> getMediaInfo(String filePath){

        HashMap<String, Long> hashMap = new HashMap<>();

        try {

            FFmpegProbeResult probeResult = ffprobe.probe(filePath);
            FFmpegStream video_stream = probeResult.getStreams().get(0);
            FFmpegStream audio_stream = probeResult.getStreams().get(1);

            hashMap.put("video.bitrate", video_stream.bit_rate);
            hashMap.put("video.fps", video_stream.avg_frame_rate.longValue());
            hashMap.put("video.width", (long) video_stream.width);
            hashMap.put("video.height", (long) video_stream.height);
            hashMap.put("audio.bitrate", audio_stream.bit_rate);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}
