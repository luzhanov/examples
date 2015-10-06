package com.luzhanov.gamebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({Config.class})
public class GameBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameBookApplication.class, args);
    }

}
