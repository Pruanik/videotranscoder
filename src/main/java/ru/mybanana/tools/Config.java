package ru.mybanana.tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config instance;
    private Properties property;
    private FileInputStream file;

    private Config(){
        try {

            file = new FileInputStream("src/main/resources/config.properties");
            property = new Properties();
            property.load(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Config getInstance(){
        if(instance==null){
            instance = new Config();
        }
        return instance;
    }

    public String getFFmpegLocation(){
        return property.getProperty("ffmpeg.location");
    }

    public String getFFprobeLocation(){
        return property.getProperty("ffprobe.location");
    }

}
