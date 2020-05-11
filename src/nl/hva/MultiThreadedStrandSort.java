package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

public class MultiThreadedStrandSort {
    // note: the input list is destroyed
    public static <Integer extends Comparable<? super Integer>>
    LinkedList<Integer> strandSort(LinkedList<Integer> list) {
        if (list.size() <= 1) return list;

        LinkedList<Integer> result = new LinkedList<Integer>();
        while (list.size() > 0) {
            LinkedList<Integer> sorted = new LinkedList<Integer>();
            sorted.add(list.removeFirst()); //same as remove() or remove(0)
            for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
                Integer elem = it.next();
                if (sorted.peekLast().compareTo(elem) <= 0) {
                    sorted.addLast(elem); //same as add(elem) or add(0, elem)
                    it.remove();
                }
            }
            result = merge(sorted, result);
        }
        return result;
    }

    private static <Integer extends Comparable<? super Integer>>
    LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        while (!left.isEmpty() && !right.isEmpty()) {
            //change the direction of this comparison to change the direction of the sort
            if (left.peek().compareTo(right.peek()) <= 0)
                result.add(left.remove());
            else
                result.add(right.remove());
        }
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
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
