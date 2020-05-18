package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static helpers.StandSortHelperMethods.merge;
import static helpers.StandSortHelperMethods.orderList;

public class DoubleThreadedStrandSort extends Thread {
    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        LinkedList<Integer> firstPart = new LinkedList<>(list.subList(0, (list.size() + 1) / 2));
        LinkedList<Integer> secondPart = new LinkedList<>(list.subList((list.size() + 1) / 2, list.size()));
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

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        output = merge(firstResultList, secondResultList);

        return output;
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        TextFileReader reader = new TextFileReader();
        LinkedList<Integer> smallestList = reader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList = reader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList = reader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = reader.readFile("src/datasets/biggestDataSet.txt");
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
