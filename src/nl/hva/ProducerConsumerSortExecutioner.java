package nl.hva;

import datasets.TextFileReader;
import nl.hva.producerConsumer.DoubleThreadedProducerConsumerSort;
import nl.hva.producerConsumer.FourThreadedProducerConsumerSort;
import nl.hva.producerConsumer.ProducerConsumerInterface;
import nl.hva.producerConsumer.ProducerConsumerSort;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ProducerConsumerSortExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        ProducerConsumerSort producerConsumerSort = new ProducerConsumerSort();
        DoubleThreadedProducerConsumerSort doubleThreadedSorter = new DoubleThreadedProducerConsumerSort();
        //FourThreadedProducerConsumerSort fourThreadedSorter = new FourThreadedProducerConsumerSort();

        long elapsedSmallestTime = calculateTime(doubleThreadedSorter, "src/datasets/smallestDataSet.txt");
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - elapsedSmallestTime));

        long elapsedMiddleTime = calculateTime(doubleThreadedSorter, "src/datasets/middleDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedMiddleTime));

        long elapsedBigTime = calculateTime(doubleThreadedSorter, "src/datasets/bigDataSet.txt");
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBigTime));

        long elapsedBiggestTime = calculateTime(doubleThreadedSorter, "src/datasets/biggestDataSet.txt");
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBiggestTime));
    }

    private static long calculateTime(ProducerConsumerInterface producerConsumer, String filePath) throws FileNotFoundException, InterruptedException {
        LinkedList<Integer> unsortedList = TextFileReader.readFile(filePath);

        long elapsedTime = System.currentTimeMillis();
        producerConsumer.starter(unsortedList);

        return elapsedTime;
    }
}
