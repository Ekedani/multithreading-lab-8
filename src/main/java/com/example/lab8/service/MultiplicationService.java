package com.example.lab8.service;

import com.example.lab8.model.Matrix;
import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.service.algorithm.FoxMatrixMultiplicator;
import org.springframework.stereotype.Service;


@Service
public class MultiplicationService {
    private final static int BLOCKS_NUM_SQRT = 4;

    public double[][] multiplyMatricesFromClient(MultiplicationRequest multiplicationRequest) {
        var a = new Matrix(multiplicationRequest.a);
        var b = new Matrix(multiplicationRequest.b);
        var c = multiplyMatrices(a, b);
        return c.data;
    }

    public double[][] multiplyMatricesFromServer(String matrixSize) {
        var size = Integer.parseInt(matrixSize);
        var a = readMatrixFromFile(size);
        var b = readMatrixFromFile(size);
        var c = multiplyMatrices(a, b);
        return c.data;
    }

    private Matrix readMatrixFromFile(int matrixSize) {
        return new Matrix(matrixSize);
    }

    private Matrix multiplyMatrices(Matrix a, Matrix b) {
        var matrixMultiplicator = new FoxMatrixMultiplicator(BLOCKS_NUM_SQRT);
        return matrixMultiplicator.multiply(a, b);
    }
}
