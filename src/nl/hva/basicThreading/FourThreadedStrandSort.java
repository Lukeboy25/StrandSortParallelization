package nl.hva.basicThreading;

import java.util.LinkedList;
import java.util.List;

import static helpers.StrandSortHelperMethods.orderList;
import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;

public class FourThreadedStrandSort extends Thread {

    private volatile LinkedList<Integer> outputPartOneTwo;
    private volatile LinkedList<Integer> outputPartThreeFour;

    private LinkedList<Integer> firstResultList;
    private LinkedList<Integer> secondResultList;
    private LinkedList<Integer> thirdResultList;
    private LinkedList<Integer> fourthResultList;

    public LinkedList<Integer> strandSort(LinkedList<Integer> list) {
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
}
