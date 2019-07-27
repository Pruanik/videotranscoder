package ru.mybanana.tools;

import java.io.File;

public class dirCreator {
    public static void create(String path){
        File dir = new File(path);

        if(!dir.exists()){
            dir.mkdir();
        }
    }
}
