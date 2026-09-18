package com.example.arizonatweetbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArizonaTweetBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArizonaTweetBotApplication.class, args);
    }

}
