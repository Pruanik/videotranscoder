package ru.mybanana.media.controls;

import ru.mybanana.tools.BashOperation;
import ru.mybanana.tools.Config;

public class CmdExecutorControl {

    public static void packToMP4(String inputStreamPath, String outputContainerPath ){
        StringBuilder command = new StringBuilder();

        command.append("MP4Box -add ");
        command.append(inputStreamPath);
        command.append(" ");
        command.append(outputContainerPath);

        BashOperation.console(command.toString());
    }

    public static void Mp4ToDASH(String videoContainerPath, String audioContainerPath, String dashMpdPath){
        StringBuilder command = new StringBuilder();

        command.append("MP4Box -dash ");
        command.append(Config.getInstance().getSettingsAppSegmentSize()*1000);
        command.append(" -frag ");
        command.append(Config.getInstance().getSettingsAppSegmentSize()*1000);
        command.append(" -rap ");
        command.append(" -segment-name %s/seg_ ");
        command.append(" -url-template ");
        command.append(" -out ");
        command.append(dashMpdPath);
        command.append(" ");
        command.append(videoContainerPath);
        command.append(" ");
        command.append(audioContainerPath);

        BashOperation.console(command.toString());
    }
}
