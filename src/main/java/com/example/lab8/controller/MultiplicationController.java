package com.example.lab8.controller;

import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.service.MultiplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MultiplicationController {
    private final MultiplicationService multiplicationService;

    public MultiplicationController(MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping("/multiply-client")
    public String clientMultiplication(@RequestBody MultiplicationRequest multiplicationRequest) {
        return "ok";
    }

    @GetMapping("/multiply-server")
    public double[][] serverMultiplication(@RequestParam("size") int size) {
        return multiplicationService.multiplyMatricesFromServer(size);
    }
}
