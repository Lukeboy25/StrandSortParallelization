package nl.hva.basicThreading;

import java.util.LinkedList;

import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class DoubleThreadedStrandSort extends Thread {

    private LinkedList<Integer> firstResultList;
    private LinkedList<Integer> secondResultList;

    public LinkedList<Integer> strandSort(LinkedList<Integer> list) {
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