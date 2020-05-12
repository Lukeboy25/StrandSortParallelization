package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

import static helpers.StandSortMerger.merge;

public class Strand {
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



    public static void main(String[] args) throws FileNotFoundException {
        TextFileReader dataset = new TextFileReader();
        LinkedList<Integer> smallestList = (LinkedList<Integer>) dataset.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList = (LinkedList<Integer>) dataset.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList = (LinkedList<Integer>) dataset.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = (LinkedList<Integer>) dataset.readFile("src/datasets/biggestDataSet.txt");
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
