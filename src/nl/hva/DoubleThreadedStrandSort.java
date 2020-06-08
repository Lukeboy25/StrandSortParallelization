package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import static helpers.StandSortHelperMethods.merge;
import static helpers.StandSortHelperMethods.orderList;

public class DoubleThreadedStrandSort extends Thread {

    private static LinkedList<Integer> firstResultList;
    private static LinkedList<Integer> secondResultList;

    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        firstResultList = new LinkedList<>();
        secondResultList = new LinkedList<>();

        LinkedList<Integer> firstPart = new LinkedList<>(list.subList(0, (list.size() + 1) / 2));
        LinkedList<Integer> secondPart = new LinkedList<>(list.subList((list.size() + 1) / 2, list.size()));
        LinkedList<Integer> output;

        Thread firstThread = new Thread(() -> {
            firstResultList = orderList(firstPart, firstResultList);
        });
        firstThread.start();

        Thread secondThread = new Thread(() -> {
            secondResultList = orderList(secondPart, secondResultList);
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
}
