package nl.hva.producerConsumer;

import datasets.TextFileReader;
import helpers.ProducerConsumerHelper;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ProducerConsumerSortExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        ProducerConsumerHelper producerConsumerHelper = new ProducerConsumerHelper();
        int amountOfThreads = 8;

        long elapsedSmallestTime = calculateTime(producerConsumerHelper, amountOfThreads, "src/datasets/smallestDataSet.txt");
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - elapsedSmallestTime));

        long elapsedMiddleTime = calculateTime(producerConsumerHelper, amountOfThreads, "src/datasets/middleDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedMiddleTime));

        long elapsedBigTime = calculateTime(producerConsumerHelper, amountOfThreads, "src/datasets/bigDataSet.txt");
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBigTime));

        long elapsedBiggestTime = calculateTime(producerConsumerHelper, amountOfThreads,"src/datasets/biggestDataSet.txt");
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBiggestTime));
    }

    private static long calculateTime(ProducerConsumerHelper producerConsumerHelper, int amountOfThreads, String filePath) throws FileNotFoundException, InterruptedException {
        LinkedList<Integer> unsortedList = TextFileReader.readFile(filePath);

        long elapsedTime = System.currentTimeMillis();
        producerConsumerHelper.starter(unsortedList, amountOfThreads);

        return elapsedTime;
    }
}
