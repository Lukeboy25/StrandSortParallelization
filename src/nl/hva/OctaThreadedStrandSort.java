package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static helpers.PartitionLinkedList.partition;
import static helpers.StandSortHelperMethods.merge;
import static helpers.StandSortHelperMethods.orderList;

public class OctaThreadedStrandSort extends Thread {
    private static final int NUMBER_OF_THREADS = 4;

    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        List<LinkedList<Integer>> partitions = partition(list, list.size() / 4);
        AtomicReference<LinkedList<Integer>> outputPartOneTwo = new AtomicReference<>(new LinkedList<>());
        AtomicReference<LinkedList<Integer>> outputPartThreeFour = new AtomicReference<>(new LinkedList<>());
        AtomicReference<LinkedList<Integer>> output = new AtomicReference<>(new LinkedList<>());

        ArrayList<Thread> tasks = new ArrayList<>(NUMBER_OF_THREADS);
        List<LinkedList<Integer>> listParts = new ArrayList<>(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            LinkedList<Integer> listPart = partitions.get(i);
            LinkedList<Integer> resultList = new LinkedList<>();
            tasks.add(new Thread(() -> {
                orderList(listPart, resultList);
            }));
            tasks.get(i).start();
            listParts.add(listPart);
        }

        try {
            tasks.get(0).join();
            tasks.get(1).join();

            Thread fifthThread = new Thread(() -> {
                outputPartOneTwo.set(merge(listParts.get(0), listParts.get(1)));
            });
            fifthThread.start();

            tasks.get(2).join();
            tasks.get(3).join();

            Thread sixthThread = new Thread(() -> {
                outputPartThreeFour.set(merge(listParts.get(2), listParts.get(3)));
            });
            sixthThread.start();

            fifthThread.join();
            sixthThread.join();

            Thread seventhThread = new Thread(() -> {
                output.set(merge(outputPartOneTwo.get(), outputPartThreeFour.get()));
            });
            seventhThread.start();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        return output.get();
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
