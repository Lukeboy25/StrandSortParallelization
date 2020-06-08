package nl.hva.basicThreading;

import java.util.LinkedList;
import java.util.List;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class DoubleThreadedStrandSort extends Thread {
    private final int NUMBER_OF_THREADS = 2;

    private LinkedList<Integer> firstResultList;
    private LinkedList<Integer> secondResultList;

    public LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        firstResultList = new LinkedList<>();
        secondResultList = new LinkedList<>();

        List<LinkedList<Integer>> partitions = partition(list, list.size() / NUMBER_OF_THREADS);
        LinkedList<Integer> firstPart = partitions.get(0);
        LinkedList<Integer> secondPart = partitions.get(1);
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
