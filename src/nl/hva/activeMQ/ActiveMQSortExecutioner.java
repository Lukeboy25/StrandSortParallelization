package nl.hva.activeMQ;

import datasets.TextFileReader;
import helpers.ActiveMQHelper;

import javax.jms.JMSException;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class ActiveMQSortExecutioner {

    private static ActiveMQHelper activeMQHelper = new ActiveMQHelper();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, JMSException {
        int amountOfThreads = 2;

        long elapsedSmallestTime = calculateTime(amountOfThreads, "src/datasets/smallestDataSet.txt");
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - elapsedSmallestTime));

        long elapsedMiddleTime = calculateTime(amountOfThreads, "src/datasets/middleDataSet.txt");
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - elapsedMiddleTime));

        long elapsedBigTime = calculateTime(amountOfThreads, "src/datasets/bigDataSet.txt");
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBigTime));

        long elapsedBiggestTime = calculateTime(amountOfThreads,"src/datasets/biggestDataSet.txt");
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - elapsedBiggestTime));
    }

    private static long calculateTime(int amountOfThreads, String filePath) throws FileNotFoundException, InterruptedException, JMSException {
        LinkedList<Integer> unsortedList = TextFileReader.readFile(filePath);

        long elapsedTime = System.currentTimeMillis();
        activeMQHelper.starter(unsortedList, amountOfThreads);

        return elapsedTime;
    }
}
