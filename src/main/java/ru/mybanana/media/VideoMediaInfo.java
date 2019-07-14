package ru.mybanana.media;

public class VideoMediaInfo {

    private Long videoBitrate;
    private Integer videoFps;
    private Integer videoWidth;
    private Integer videoHeight;
    private Long audioBitrate;

    public VideoMediaInfo(VideoMediaInfo object){
        this.setVideoBitrate(object.getVideoBitrate());
        this.setVideoFps(object.getVideoFps());
        this.setVideoWidth(object.getVideoWidth());
        this.setVideoHeight(object.getVideoHeight());
        this.setAudioBitrate(object.getAudioBitrate());
    }

    public VideoMediaInfo(){}

    public Long getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(Long videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public Integer getVideoFps() {
        return videoFps;
    }

    public void setVideoFps(Integer videoFps) {
        this.videoFps = videoFps;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public Long getAudioBitrate() {
        return audioBitrate;
    }

    public void setAudioBitrate(Long audioBitrate) {
        this.audioBitrate = audioBitrate;
    }
}
