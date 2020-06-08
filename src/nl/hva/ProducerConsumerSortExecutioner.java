package nl.hva;

import datasets.TextFileReader;
import nl.hva.producerConsumer.ProducerConsumerInterface;
import nl.hva.producerConsumer.ProducerConsumerSort;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ProducerConsumerSortExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        ProducerConsumerSort sorter = new ProducerConsumerSort();

        long elapsedSmallestTime = calculateTime(sorter, "src/datasets/smallestDataSet.txt");
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - elapsedSmallestTime));

        long elapsedMiddleTime = calculateTime(sorter, "src/datasets/middleDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedMiddleTime));

        long elapsedBigTime = calculateTime(sorter, "src/datasets/bigDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBigTime));

        long elapsedBiggestTime = calculateTime(sorter, "src/datasets/biggestDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBiggestTime));
    }

    private static long calculateTime(ProducerConsumerInterface producerConsumer, String filePath) throws FileNotFoundException, InterruptedException {
        LinkedList<Integer> unsortedList = TextFileReader.readFile(filePath);

        long elapsedTime = System.currentTimeMillis();
        LinkedList<Integer> producerList = producerConsumer.produce(unsortedList);
        producerConsumer.consume(producerList);

        return elapsedTime;
    }
}
