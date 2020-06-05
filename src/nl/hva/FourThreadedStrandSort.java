package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static helpers.StandSortHelperMethods.orderList;
import static helpers.PartitionLinkedList.partition;
import static helpers.StandSortHelperMethods.merge;

public class FourThreadedStrandSort extends Thread {

    private static volatile LinkedList<Integer> outputPartOneTwo;
    private static volatile LinkedList<Integer> outputPartThreeFour;

    private static LinkedList<Integer> firstResultList;
    private static LinkedList<Integer> secondResultList;
    private static LinkedList<Integer> thirdResultList;
    private static LinkedList<Integer> fourthResultList;

    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        outputPartOneTwo = new LinkedList<>();
        outputPartThreeFour = new LinkedList<>();

        firstResultList = new LinkedList<>();
        secondResultList = new LinkedList<>();
        thirdResultList = new LinkedList<>();
        fourthResultList = new LinkedList<>();

        List<LinkedList<Integer>> partitions = partition(list, list.size() / 4);
        LinkedList<Integer> firstPart = partitions.get(0);
        LinkedList<Integer> secondPart = partitions.get(1);
        LinkedList<Integer> thirdPart = partitions.get(2);
        LinkedList<Integer> fourthPart = partitions.get(3);
        LinkedList<Integer> output;

        Thread firstThread = new Thread(() -> {
            firstResultList = orderList(firstPart, firstResultList);
        });
        firstThread.start();

        Thread secondThread = new Thread(() -> {
            secondResultList = orderList(secondPart, secondResultList);
        });
        secondThread.start();

        Thread thirdThread = new Thread(() -> {
            thirdResultList = orderList(thirdPart, thirdResultList);
        });
        thirdThread.start();

        Thread fourthThread = new Thread(() -> {
            fourthResultList = orderList(fourthPart, fourthResultList);
        });
        fourthThread.start();

        try {
            firstThread.join();
            secondThread.join();

            if (!firstThread.isAlive() && !secondThread.isAlive()) {
                firstThread = new Thread(() -> {
                    outputPartOneTwo = merge(firstResultList, secondResultList);
                });
                firstThread.start();
            }

            thirdThread.join();
            fourthThread.join();

            if (!thirdThread.isAlive() && !fourthThread.isAlive()) {
                thirdThread = new Thread(() -> {
                    outputPartThreeFour = merge(thirdResultList, fourthResultList);
                });
                thirdThread.start();
            }

            firstThread.join();
            thirdThread.join();

        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
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
