package com.example.tapchikhcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.tapchikhcn")
public class TapchikhcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TapchikhcnApplication.class, args);
    }

}
