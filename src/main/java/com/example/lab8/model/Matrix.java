package com.example.lab8.model;

import java.util.Random;

public class Matrix {
    private final int numRows;
    private final int numCols;
    public double[][] data;

    public Matrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.data = new double[numRows][numCols];
    }

    public Matrix(int size) {
        this(size, size);
    }

    public Matrix(double[][] data) {
        this.numRows = data.length;
        this.numCols = data[0].length;
        this.data = data;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public void generateRandomMatrix(double low, double high) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = Math.floor(low + (high - low) * random.nextDouble());
            }
        }
    }

    public double[][][][] getSquareBlockSplit(int blocksNumSqrt) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][this.numRows][this.numCols];
        final int blockSize = Math.ceilDiv(this.numRows, blocksNumSqrt);
        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                matrixBlocks[i][j] = getSquareBlock(i * blockSize, j * blockSize, blockSize);
            }
        }
        return matrixBlocks;
    }

    public double[][][][] getSquareBlockSplit(int blocksNumSqrt, int blockSize) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][this.numRows][this.numCols];
        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                matrixBlocks[i][j] = getSquareBlock(i * blockSize, j * blockSize, blockSize);
            }
        }
        return matrixBlocks;
    }

    private double[][] getSquareBlock(int startRow, int startCol, int size) {
        double[][] result = new double[size][size];
        for (int i = 0; i < size && (i + startRow < numRows); i++) {
            for (int j = 0; j < size && (j + startCol < numCols); j++) {
                result[i][j] = this.data[i + startRow][j + startCol];
            }
        }
        return result;
    }
}
