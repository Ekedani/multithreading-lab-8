package com.example.lab8.controller;

import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.model.MultiplicationResponse;
import com.example.lab8.service.MultiplicationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MultiplicationController {
    private final MultiplicationService multiplicationService;

    public MultiplicationController(MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping("/multiply-client")
    public MultiplicationResponse clientMultiplication(@RequestBody MultiplicationRequest multiplicationRequest) {
        var multiplicationResult = multiplicationService.multiplyMatricesFromClient(multiplicationRequest);
        return new MultiplicationResponse(multiplicationResult);
    }

    @GetMapping("/multiply-server")
    public MultiplicationResponse serverMultiplication(@RequestParam("size") String size) {
        var multiplicationResult = multiplicationService.multiplyMatricesFromServer(size);
        return new MultiplicationResponse(multiplicationResult);
    }
}
