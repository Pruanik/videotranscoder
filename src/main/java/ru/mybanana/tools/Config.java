package ru.mybanana.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    Properties property = new Properties();
    FileInputStream file;

    public Config(){
        try {

            file = new FileInputStream("src/main/resources/config.properties");
            property.load(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getFFmpegLocation(){
        return property.getProperty("ffmpeg.location");
    }

    public String getFFprobeLocation(){
        return property.getProperty("ffprobe.location");
    }

}
