package com.example.lab8.service.algorithm;

public class FoxSync {
    private final double[][][][] aBlocks;
    private final double[][][][] bBlocks;
    private final FoxThread[][] threads;
    private int pausedThreads = 0;
    private int finishedIterations = 0;

    public FoxSync(double[][][][] aBlocks, double[][][][] bBlocks, FoxThread[][] threads) {
        this.aBlocks = aBlocks;
        this.bBlocks = bBlocks;
        this.threads = threads;
    }

    public synchronized void waitForNextIteration(int iteration) {
        this.pausedThreads++;
        while (pausedThreads < threads.length * threads[0].length) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (iteration < finishedIterations) {
                break;
            }
        }
        if (pausedThreads == threads.length * threads[0].length) {
            updateThreads(iteration);
            pausedThreads = 0;
            finishedIterations++;
            notifyAll();
        }
    }

    private void updateThreads(int iteration) {
        for (int i = 0; i < threads.length; i++) {
            int k = (i + 1 + iteration) % threads.length;
            for (int j = 0; j < threads[0].length; j++) {
                threads[i][j].setABlock(aBlocks[i][k]);
                threads[i][j].setBBlock(bBlocks[k][j]);
            }
        }
    }
}
