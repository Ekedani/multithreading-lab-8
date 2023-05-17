package com.example.lab8.service.algorithm;

public class FoxThread extends Thread {
    private double[][] aBlock;
    private double[][] bBlock;
    private final double[][] result;
    private final int iterations;
    private final FoxSync sync;
    private int operations = 0;

    public FoxThread(double[][] aBlock, double[][] bBlock, int iterations, FoxSync sync) {
        this.aBlock = aBlock;
        this.bBlock = bBlock;
        this.result = new double[aBlock.length][aBlock.length];
        this.iterations = iterations;
        this.sync = sync;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            calculateSubtaskIteration();
            sync.waitForNextIteration(i);
        }
    }

    private void calculateSubtaskIteration() {
        for (int i = 0; i < aBlock.length; i++) {
            for (int j = 0; j < bBlock[0].length; j++) {
                for (int k = 0; k < bBlock.length; k++) {
                    result[i][j] += aBlock[i][k] * bBlock[k][j];
                    operations++;
                }
            }
        }
    }

    public double[][] getResult() {
        return result;
    }

    public void setABlock(double[][] aBlock) {
        this.aBlock = aBlock;
    }

    public void setBBlock(double[][] bBlock) {
        this.bBlock = bBlock;
    }
}
