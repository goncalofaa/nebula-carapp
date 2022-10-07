package com.nebula.nebulacarapp;

import com.nebula.nebulacarapp.model.Car;
import com.nebula.nebulacarapp.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NebulaCarappApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaCarappApplication.class, args);
    }



}
