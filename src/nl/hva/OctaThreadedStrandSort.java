package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.*;

import static helpers.PartitionLinkedList.partition;
import static helpers.StandSortHelperMethods.merge;
import static helpers.StandSortHelperMethods.orderList;

public class OctaThreadedStrandSort extends Thread {
    private static final int NUMBER_OF_THREADS = 8;

    private static volatile LinkedList<Integer> outputPartOneTwo;
    private static volatile LinkedList<Integer> outputPartThreeFour;
    private static volatile LinkedList<Integer> outputPartFiveSix;
    private static volatile LinkedList<Integer> outputPartSevenEight;
    private static volatile LinkedList<Integer> outputPartOneTwoThreeFour;
    private static volatile LinkedList<Integer> outputPartFiveSixSevenEight;
    private static volatile LinkedList<Integer> output = new LinkedList<>();

    private static volatile LinkedList<Integer>[] resultListParts = new LinkedList[NUMBER_OF_THREADS];

    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        outputPartOneTwo = new LinkedList<>();
        outputPartThreeFour = new LinkedList<>();
        outputPartFiveSix = new LinkedList<>();
        outputPartSevenEight = new LinkedList<>();
        outputPartOneTwoThreeFour = new LinkedList<>();
        outputPartFiveSixSevenEight = new LinkedList<>();

        List<LinkedList<Integer>> partitions = partition(list, list.size() / NUMBER_OF_THREADS);
        ArrayList<Thread> tasks = new ArrayList<>(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            int counter = i;
            LinkedList<Integer> listPart = partitions.get(i);
            resultListParts[i] = new LinkedList<>();

            tasks.add(new Thread(() -> resultListParts[counter] = orderList(listPart, resultListParts[counter])));
            tasks.get(i).start();
        }

        try {
            Thread firstThread = tasks.get(0);
            Thread secondThread = tasks.get(1);
            firstThread.join();
            secondThread.join();

            if (!firstThread.isAlive() && !secondThread.isAlive()) {
                firstThread = new Thread(() -> {
                    outputPartOneTwo = merge(resultListParts[0], resultListParts[1]);
                });
                firstThread.start();
            }

            Thread thirdThread = tasks.get(2);
            Thread fourthThread = tasks.get(3);
            thirdThread.join();
            fourthThread.join();

            if (!thirdThread.isAlive() && !fourthThread.isAlive()) {
                thirdThread = new Thread(() -> {
                    outputPartThreeFour = merge(resultListParts[2], resultListParts[3]);
                });
                thirdThread.start();
            }

            Thread fifthThread = tasks.get(4);
            Thread sixthThread = tasks.get(5);
            fifthThread.join();
            sixthThread.join();

            if (!fifthThread.isAlive() && !sixthThread.isAlive()) {
                fifthThread = new Thread(() -> {
                    outputPartFiveSix = merge(resultListParts[4], resultListParts[5]);
                });
                fifthThread.start();
            }

            Thread seventhThread = tasks.get(6);
            Thread eightThread = tasks.get(7);
            seventhThread.join();
            eightThread.join();

            if (!seventhThread.isAlive() && !eightThread.isAlive()) {
                seventhThread = new Thread(() -> {
                    outputPartSevenEight = merge(resultListParts[6], resultListParts[7]);
                });
                seventhThread.start();
            }

            try {
                firstThread.join();
                thirdThread.join();
            } catch (InterruptedException e) {
                System.out.println("First-Four thread Interrupted");
            }

            if (!firstThread.isAlive() && !thirdThread.isAlive()) {
                firstThread = new Thread(() -> {
                    outputPartOneTwoThreeFour = merge(outputPartOneTwo, outputPartThreeFour);
                });
                firstThread.start();
            }

            try {
                fifthThread.join();
                seventhThread.join();
            } catch (InterruptedException e) {
                System.out.println("Fifth-Eight thread Interrupted");
            }

            if (!fifthThread.isAlive() && !seventhThread.isAlive()) {
                fifthThread = new Thread(() -> {
                    outputPartFiveSixSevenEight = merge(outputPartFiveSix, outputPartSevenEight);
                });
                fifthThread.start();
            }

            try {
                firstThread.join();
                fifthThread.join();
            } catch (InterruptedException e) {
                System.out.println("Fifth-Eight thread Interrupted");
            }

            output = merge(outputPartOneTwoThreeFour, outputPartFiveSixSevenEight);

        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

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
