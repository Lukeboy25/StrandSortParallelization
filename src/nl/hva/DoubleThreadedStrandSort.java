package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

import static helpers.StandSortMerger.merge;

public class DoubleThreadedStrandSort extends Thread {
    public static <Integer extends Comparable<? super Integer>>
    LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        LinkedList<Integer> firstPart = new LinkedList<Integer>(list.subList(0, (list.size() + 1) / 2));
        LinkedList<Integer> secondPart = new LinkedList<Integer>(list.subList((list.size() + 1) / 2, list.size()));
        LinkedList<Integer> output = new LinkedList<Integer>();

        AtomicReference<LinkedList<Integer>> firstResultList = new AtomicReference<>(new LinkedList<Integer>());
        Thread firstThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)firstPart, (AtomicReference)firstResultList);
        });
        firstThread.start();

        AtomicReference<LinkedList<Integer>> secondResultList = new AtomicReference<>(new LinkedList<>());
        Thread secondThread = new Thread(() -> {
            orderList((LinkedList<java.lang.Integer>)secondPart, (AtomicReference)secondResultList);
        });
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        output = merge(firstResultList.get(), secondResultList.get());

        return output;
    }

    private static void orderList(LinkedList<Integer> listPart, AtomicReference<LinkedList> resultList) {
        while (listPart.size() > 0) {
            LinkedList<Integer> subList = new LinkedList<Integer>();

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
        long start = 0;

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
