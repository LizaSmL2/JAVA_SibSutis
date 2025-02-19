package org.example.homework2;

public class Main {
    public static void main(String[] args) {
        int numberOfThreads = 10;
        int calculationLength = 30;
        int sleepTime = 10;

        for (int i = 0; i < numberOfThreads; i++) {
            new MyThread(i + 1, calculationLength, sleepTime).start(); // Запускаем поток
        }
    }
}

class MyThread extends Thread {
    private final int threadNumber;
    private final int calculationLength;
    private final int sleepTime;

    public MyThread(int threadNumber, int calculationLength, int sleepTime) {
        this.threadNumber = threadNumber;
        this.calculationLength = calculationLength;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        StringBuilder progressBar = new StringBuilder("Thread |" + threadNumber + " [");

        for (int j = 0; j < calculationLength; j++) {
            progressBar.append("=");
            System.out.print("\r" + progressBar.toString() + " ");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}