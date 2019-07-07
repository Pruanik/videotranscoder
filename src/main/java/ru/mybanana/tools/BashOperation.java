package ru.mybanana.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BashOperation {
    private static void console(){
        ProcessBuilder processBuilder = new ProcessBuilder("ping", "google.com");
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line + "\n");
            }

            if (process.waitFor() == 0) {
                System.out.println("complete!");
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
