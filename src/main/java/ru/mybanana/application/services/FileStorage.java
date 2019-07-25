package ru.mybanana.application.services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mybanana.tools.Config;
import ru.mybanana.tools.Randomizer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileStorage {

    private String lastName = new String();

    public void addName(String name){
        lastName = name;
    }

    public String getName(){
        return lastName;
    }

    public void init() {

    }

    public void store(MultipartFile file) {
        if(!file.isEmpty()){
            try{
                String fileName = Config.getInstance().getFilestoragePath()+Randomizer.getRandomString(10)+"-uploaded";
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                stream.write(bytes);
                stream.close();
                System.out.println("Success");
                addName(fileName);
            } catch (Exception e){
                System.out.println("Error");
            }
        }

    }

    public Stream<Path> loadAll() {
        return null;
    }

    public Path load(String filename) {
        return null;
    }

    public Resource loadAsResource(String filename) {
        return null;
    }

    public void deleteAll() {

    }
}
