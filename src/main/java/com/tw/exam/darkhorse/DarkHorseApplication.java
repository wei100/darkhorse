package com.tw.exam.darkhorse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DarkHorseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkHorseApplication.class, args);
    }
}
