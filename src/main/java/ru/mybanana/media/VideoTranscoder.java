package ru.mybanana.media;

import java.io.IOException;

public class VideoTranscoder {

    private Video video;

    public VideoTranscoder(String InputFilePath){

        video = new Video(InputFilePath);

    }

    public void start() throws IOException {
        try {

            video
                    .transformVideo()
                    .transformAudio()
                    .packToMP4()
                    .makeDASH()
                    .cleanTmpFiles();

        } catch (NullPointerException e) {

            e.printStackTrace();

        }
    }
}
