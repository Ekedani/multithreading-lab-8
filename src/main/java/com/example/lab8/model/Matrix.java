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
        return numCols;
    }

    public double[][][][] getSquareBlockSplit(int blocksNumSqrt) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][numRows][numCols];
        final int blockSize = Math.ceilDiv(numRows, blocksNumSqrt);
        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                matrixBlocks[i][j] = getSquareBlock(i * blockSize, j * blockSize, blockSize);
            }
        }
        return matrixBlocks;
    }

    public double[][][][] getSquareBlockSplit(int blocksNumSqrt, int blockSize) {
        double[][][][] matrixBlocks = new double[blocksNumSqrt][blocksNumSqrt][numRows][numCols];
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
                result[i][j] = data[i + startRow][j + startCol];
            }
        }
        return result;
    }

    static public Matrix joinSquareBlockSplit(int numRows, int numCols, double[][][][] blocks) {
        Matrix matrix = new Matrix(numRows, numCols);
        final int blockSize = blocks[0][0].length;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                double[][] subMatrix = blocks[i][j];
                int subMatrixStartRow = i * blockSize;
                int subMatrixStartCol = j * blockSize;
                for (int k = 0; k < blockSize && k + subMatrixStartRow < numRows; k++) {
                    for (int l = 0; l < blockSize && l + subMatrixStartCol < numCols; l++) {
                        matrix.data[k + subMatrixStartRow][l + subMatrixStartCol] = subMatrix[k][l];
                    }
                }
            }
        }
        return matrix;
    }

    public void generateRandomMatrix(double low, double high) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = Math.floor(low + (high - low) * random.nextDouble());
            }
        }
    }

    public void generateConstantMatrix(double constant) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                data[i][j] = constant;
            }
        }
    }
}
