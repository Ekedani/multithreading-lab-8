package com.example.lab8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Lab8Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab8Application.class, args);
    }

    @GetMapping("/multiply-client")
    public String clientMultiplication() {
        return "ok";
    }

    @GetMapping("/multiply-server")
    public String serverMultiplication() {
        return "not ok";
    }
}
