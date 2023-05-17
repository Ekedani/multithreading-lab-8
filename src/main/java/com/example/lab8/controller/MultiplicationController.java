package com.example.lab8.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MultiplicationController {
    @GetMapping("/multiply-client")
    public String clientMultiplication() {
        return "ok";
    }

    @GetMapping("/multiply-server")
    public String serverMultiplication() {
        return "not ok";
    }
}
