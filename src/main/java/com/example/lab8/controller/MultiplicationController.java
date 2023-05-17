package com.example.lab8.controller;

import com.example.lab8.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MultiplicationController {
    @Autowired
    private MultiplicationService multiplicationService;

    @PostMapping("/multiply-client")
    public String clientMultiplication() {
        return "ok";
    }

    @GetMapping("/multiply-server")
    public String serverMultiplication() {
        return "not ok";
    }
}
