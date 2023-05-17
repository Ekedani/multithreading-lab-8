package com.example.lab8.service;

import com.example.lab8.model.Matrix;
import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.service.algorithm.FoxMatrixMultiplicator;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MultiplicationService {
    public double[][] multiplyMatricesFromClient(MultiplicationRequest multiplicationRequest) {
        var a = new Matrix(multiplicationRequest.a);
        var b = new Matrix(multiplicationRequest.b);
        var c = multiplyMatrices(a, b);
        return c.data;
    }

    public double[][] multiplyMatricesFromServer(int matrixSize) {
        var a = readMatrixFromFile(matrixSize);
        var b = readMatrixFromFile(matrixSize);
        var c = multiplyMatrices(a, b);
        return c.data;
    }

    private Matrix readMatrixFromFile(int matrixSize) {
        return new Matrix(matrixSize);
    }

    private Matrix multiplyMatrices(Matrix a, Matrix b) {
        var matrixMultiplicator = new FoxMatrixMultiplicator(4);
        return matrixMultiplicator.multiply(a, b);
    }
}
