package com.example.lab8.service;

import com.example.lab8.model.Matrix;
import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.service.algorithm.FoxMatrixMultiplicator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class MultiplicationService {
    private final static int BLOCKS_NUM_SQRT = 4;
    private final static String DATA_DIRECTORY = "D:\\KPI\\H1'23\\Parallel\\lab-8\\data\\";

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
        try {
            var file = new File(DATA_DIRECTORY + matrixSize + ".csv");
            var reader = new BufferedReader(new FileReader(file));
            double[][] matrix = new double[matrixSize][matrixSize];
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < matrixSize) {
                String[] values = line.split(",");
                for (int col = 0; col < matrixSize; col++) {
                    matrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
            return new Matrix(matrix);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Matrix multiplyMatrices(Matrix a, Matrix b) {
        var matrixMultiplicator = new FoxMatrixMultiplicator(BLOCKS_NUM_SQRT);
        return matrixMultiplicator.multiply(a, b);
    }
}
