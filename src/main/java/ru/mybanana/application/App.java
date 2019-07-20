package ru.mybanana.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.mybanana.application.services.SystemFileStorage;

@SpringBootApplication
public class App {

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }

    @Bean
    CommandLineRunner init(SystemFileStorage storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
