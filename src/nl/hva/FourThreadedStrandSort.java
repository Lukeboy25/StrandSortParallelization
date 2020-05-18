package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static helpers.StandSortHelperMethods.orderList;
import static helpers.PartitionLinkedList.partition;
import static helpers.StandSortHelperMethods.merge;

public class FourThreadedStrandSort extends Thread {
    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        List<LinkedList<Integer>> partitions = partition(list, list.size() / 4);
        LinkedList<Integer> firstPart = partitions.get(0);
        LinkedList<Integer> secondPart = partitions.get(1);
        LinkedList<Integer> thirdPart = partitions.get(2);
        LinkedList<Integer> fourthPart = partitions.get(3);
        LinkedList<Integer> outputPartOneTwo;
        LinkedList<Integer> outputPartThreeFour;
        LinkedList<Integer> output;

        LinkedList<Integer> firstResultList = new LinkedList<>();
        Thread firstThread = new Thread(() -> {
            orderList(firstPart, firstResultList);
        });
        firstThread.start();

        LinkedList<Integer> secondResultList = new LinkedList<>();
        Thread secondThread = new Thread(() -> {
            orderList(secondPart, secondResultList);
        });
        secondThread.start();

        LinkedList<Integer> thirdResultList = new LinkedList<>();
        Thread thirdThread = new Thread(() -> {
            orderList(thirdPart, thirdResultList);
        });
        thirdThread.start();

        LinkedList<Integer> fourthResultList = new LinkedList<>();
        Thread fourthThread = new Thread(() -> {
            orderList(fourthPart, fourthResultList);
        });
        fourthThread.start();

        try {
            firstThread.join();
            secondThread.join();
            thirdThread.join();
            fourthThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        outputPartOneTwo = merge(firstResultList, secondResultList);
        outputPartThreeFour = merge(thirdResultList, fourthResultList);
        output = merge(outputPartOneTwo, outputPartThreeFour);

        return output;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        TextFileReader reader = new TextFileReader();
        LinkedList<Integer> smallestList = (LinkedList<Integer>) reader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList = (LinkedList<Integer>) reader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList = (LinkedList<Integer>) reader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = (LinkedList<Integer>) reader.readFile("src/datasets/biggestDataSet.txt");
        long start;

        start = System.currentTimeMillis();
        strandSort(smallestList);
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        strandSort(middleList);
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        strandSort(bigList);
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        strandSort(biggestList);
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
