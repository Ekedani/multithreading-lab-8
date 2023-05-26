package com.example.lab8.service;

import com.example.lab8.model.Matrix;
import com.example.lab8.model.MultiplicationRequest;
import com.example.lab8.service.algorithm.FoxMatrixMultiplicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

@Service
public class MultiplicationService {
    private final static int BLOCKS_NUM_SQRT = 4;
    private final static String DATA_DIRECTORY = "D:\\KPI\\H1'23\\Parallel\\lab-8\\data\\";

    public double[][] multiplyMatricesFromClient(MultiplicationRequest multiplicationRequest) {
        try {
            var a = new Matrix(multiplicationRequest.a);
            var b = new Matrix(multiplicationRequest.b);
            var c = multiplyMatrices(a, b);
            return c.data;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public double[][] multiplyMatricesFromServer(String matrixSize) {
        try {
            var size = Integer.parseInt(matrixSize);
            if (size <= 0) {
                throw new Exception("Size must be positive");
            }
            var a = readMatrixFromFile(size);
            var b = readMatrixFromFile(size);
            var c = multiplyMatrices(a, b);
            return c.data;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public double[][] multiplyFileMatricesFromClient(MultipartFile fileA, MultipartFile fileB) {
        try {
            var a = convertMultipartFileToMatrix(fileA);
            var b = convertMultipartFileToMatrix(fileB);
            var c = multiplyMatrices(a, b);
            return c.data;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Matrix multiplyMatrices(Matrix a, Matrix b) {
        var matrixMultiplicator = new FoxMatrixMultiplicator(BLOCKS_NUM_SQRT);
        return matrixMultiplicator.multiply(a, b);
    }

    private Matrix convertMultipartFileToMatrix(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        String[] rows = content.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].trim().split("\\s+").length;
        double[][] matrix = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            String[] values = rows[i].trim().split("\\s+");
            for (int j = 0; j < numCols; j++) {
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }
        return new Matrix(matrix);
    }
}
