package com.filatovilia.spring.springboot.springbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAppApplication {

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(SpringBootAppApplication.class);
//        app.setLazyInitialization(true);
//        app.run(args);
        SpringApplication.run(SpringBootAppApplication.class, args);
    }

}
