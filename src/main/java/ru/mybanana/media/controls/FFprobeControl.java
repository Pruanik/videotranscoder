package ru.mybanana.media.controls;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import ru.mybanana.media.VideoMediaInfo;
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

    public VideoMediaInfo getMediaInfo(String filePath){

        VideoMediaInfo mediaInfo = new VideoMediaInfo();

        try {

            FFmpegProbeResult probeResult = ffprobe.probe(filePath);
            FFmpegStream video_stream = probeResult.getStreams().get(0);
            FFmpegStream audio_stream = probeResult.getStreams().get(1);

            mediaInfo.setVideoBitrate(video_stream.bit_rate);
            mediaInfo.setVideoFps(video_stream.avg_frame_rate.intValue());
            mediaInfo.setVideoWidth(video_stream.width);
            mediaInfo.setVideoHeight(video_stream.height);
            mediaInfo.setAudioBitrate(audio_stream.bit_rate);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaInfo;
    }
}
