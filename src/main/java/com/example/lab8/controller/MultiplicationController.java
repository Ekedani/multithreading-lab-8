package com.example.lab8.controller;

import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.model.MultiplicationResponse;
import com.example.lab8.service.MultiplicationService;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


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

    @PostMapping(value = "/multiply-client-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MultiplicationResponse clientFileMultiplication(
            @RequestParam("a") MultipartFile a,
            @RequestParam("b") MultipartFile b
    ) {
        var multiplicationResult = multiplicationService.multiplyFileMatricesFromClient(a, b);
        return new MultiplicationResponse(multiplicationResult);
    }
}
