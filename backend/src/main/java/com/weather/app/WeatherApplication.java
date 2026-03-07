package com.weather.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.additional-location", "optional:./");
        SpringApplication.run(WeatherApplication.class, args);
    }
}