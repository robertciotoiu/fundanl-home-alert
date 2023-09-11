package com.robertciotoiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeAlerter {
    public static void main(String[] args) {
        SpringApplication.run(HomeAlerter.class, args);}
}
