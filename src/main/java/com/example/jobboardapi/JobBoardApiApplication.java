package com.example.jobboardapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobBoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBoardApiApplication.class, args);
    }

}
