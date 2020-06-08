package nl.hva.basicThreading;

import java.util.*;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class OctaThreadedStrandSort extends Thread {
    private final int NUMBER_OF_THREADS = 8;

    private volatile LinkedList<Integer> outputPartOneTwo;
    private volatile LinkedList<Integer> outputPartThreeFour;
    private volatile LinkedList<Integer> outputPartFiveSix;
    private volatile LinkedList<Integer> outputPartSevenEight;
    private volatile LinkedList<Integer> outputPartOneTwoThreeFour;
    private volatile LinkedList<Integer> outputPartFiveSixSevenEight;
    private volatile LinkedList<Integer> output = new LinkedList<>();

    private  volatile LinkedList<Integer>[] resultListParts = new LinkedList[NUMBER_OF_THREADS];

    public  LinkedList<Integer> strandSort(LinkedList<Integer> list) {
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

            firstThread.join();
            thirdThread.join();

            if (!firstThread.isAlive() && !thirdThread.isAlive()) {
                firstThread = new Thread(() -> {
                    outputPartOneTwoThreeFour = merge(outputPartOneTwo, outputPartThreeFour);
                });
                firstThread.start();
            }

            fifthThread.join();
            seventhThread.join();

            if (!fifthThread.isAlive() && !seventhThread.isAlive()) {
                fifthThread = new Thread(() -> {
                    outputPartFiveSixSevenEight = merge(outputPartFiveSix, outputPartSevenEight);
                });
                fifthThread.start();
            }

            firstThread.join();
            fifthThread.join();

            output = merge(outputPartOneTwoThreeFour, outputPartFiveSixSevenEight);

        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        return output;
    }
}
