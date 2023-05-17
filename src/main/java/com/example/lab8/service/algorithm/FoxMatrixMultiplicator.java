package com.example.lab8.service.algorithm;


import com.example.lab8.model.Matrix;

public class FoxMatrixMultiplicator {
    private final int blocksNumSqrt;

    public FoxMatrixMultiplicator(int blocksNum) {
        this.blocksNumSqrt = blocksNum;
    }

    public Matrix multiply(Matrix A, Matrix B) {
        if (A.getNumCols() != B.getNumRows()) {
            throw new IllegalArgumentException("Number of columns in first matrix must match number of rows in second matrix.");
        }
        FoxThread[][] threads = createThreads(A, B);
        for (FoxThread[] thread : threads) {
            for (int j = 0; j < threads[0].length; j++) {
                thread[j].start();
            }
        }
        for (FoxThread[] thread : threads) {
            for (int j = 0; j < threads[0].length; j++) {
                try {
                    thread[j].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        double[][][][] resultBlocks = new double[blocksNumSqrt][blocksNumSqrt][][];
        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[0].length; j++) {
                resultBlocks[i][j] = threads[i][j].getResult();
            }
        }
        return Matrix.joinSquareBlockSplit(A.getNumRows(), B.getNumCols(), resultBlocks);
    }

    private FoxThread[][] createThreads(Matrix A, Matrix B) {
        double[][][][] aBlocks = A.getSquareBlockSplit(blocksNumSqrt);
        double[][][][] bBlocks = B.getSquareBlockSplit(blocksNumSqrt, aBlocks[0][0].length);

        FoxThread[][] threads = new FoxThread[blocksNumSqrt][blocksNumSqrt];
        FoxSync sync = new FoxSync(aBlocks, bBlocks, threads);

        for (int i = 0; i < blocksNumSqrt; i++) {
            for (int j = 0; j < blocksNumSqrt; j++) {
                threads[i][j] = new FoxThread(aBlocks[i][i], bBlocks[i][j], blocksNumSqrt, sync);
            }
        }
        return threads;
    }
}
