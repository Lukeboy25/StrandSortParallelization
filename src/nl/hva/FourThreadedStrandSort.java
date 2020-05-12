package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static helpers.PartitionLinkedList.partition;
import static helpers.StandSortMerger.merge;

public class FourThreadedStrandSort extends Thread {
    public static <Integer extends Comparable<? super Integer>>
    LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        List<List<Integer>> partitions = partition(list, list.size() / 4);
        List<Integer> firstPart = partitions.get(0);
        List<Integer> secondPart = partitions.get(1);
        List<Integer> thirdPart = partitions.get(2);
        List<Integer> fourthPart = partitions.get(3);
        LinkedList<Integer> outputPartOneTwo;
        LinkedList<Integer> outputPartThreeFour;
        LinkedList<Integer> output;

        AtomicReference<LinkedList<Integer>> firstResultList = new AtomicReference<>(new LinkedList<>());
        Thread firstThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)firstPart, (AtomicReference)firstResultList);
        });
        firstThread.start();

        AtomicReference<LinkedList<Integer>> secondResultList = new AtomicReference<>(new LinkedList<>());
        Thread secondThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)secondPart, (AtomicReference)secondResultList);
        });
        secondThread.start();

        AtomicReference<LinkedList<Integer>> thirdResultList = new AtomicReference<>(new LinkedList<>());
        Thread thirdThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)thirdPart, (AtomicReference)thirdResultList);
        });
        thirdThread.start();

        AtomicReference<LinkedList<Integer>> fourthResultList = new AtomicReference<>(new LinkedList<>());
        Thread fourthThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)fourthPart, (AtomicReference)fourthResultList);
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
        outputPartOneTwo = merge(firstResultList.get(), secondResultList.get());
        outputPartThreeFour = merge(thirdResultList.get(), fourthResultList.get());
        output = merge(outputPartOneTwo, outputPartThreeFour);

        return output;
    }

    private static void orderList(LinkedList<Integer> listPart, AtomicReference<LinkedList> resultList) {
        while (listPart.size() > 0) {
            LinkedList<Integer> subList = new LinkedList<>();

            subList.add(listPart.removeFirst());

            for (Iterator<Integer> it = listPart.iterator(); it.hasNext(); ) {
                Integer elem = it.next();
                if (subList.peekLast().compareTo(elem) <= 0) {
                    subList.addLast(elem);
                    it.remove();
                }
            }

            resultList.set(merge(subList, resultList.get()));
        }
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        TextFileReader reader = new TextFileReader();
        LinkedList<Integer> smallestList = (LinkedList<Integer>) reader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList = (LinkedList<Integer>) reader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList = (LinkedList<Integer>) reader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = (LinkedList<Integer>) reader.readFile("src/datasets/biggestDataSet.txt");
        long start;

        start = System.currentTimeMillis();
        System.out.println(strandSort(smallestList));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(strandSort(middleList));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(strandSort(bigList));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(strandSort(biggestList));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
