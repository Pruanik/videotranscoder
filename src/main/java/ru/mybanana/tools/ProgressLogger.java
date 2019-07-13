package ru.mybanana.tools;

import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import ru.mybanana.media.controls.FFprobeControl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ProgressLogger implements ProgressListener {

    private FFmpegProbeResult InputFileProbeResult;
    private double duration_ns;

    public ProgressLogger(String filePath) throws IOException{
        FFprobeControl FFprobe = new FFprobeControl();
        InputFileProbeResult = FFprobe.getObject().probe(filePath);
        // Using the FFmpegProbeResult determine the duration of the input
        duration_ns = InputFileProbeResult.getFormat().duration * TimeUnit.SECONDS.toNanos(1);
    }

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
}
