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

        LinkedList<Integer> list1 = new LinkedList<Integer>(list.subList(0, (list.size() + 1)/2));
        LinkedList<Integer> list2 = new LinkedList<Integer>(list.subList((list.size() + 1)/2, list.size()));

        LinkedList<Integer> resultList1 = new LinkedList<Integer>();
        LinkedList<Integer> resultList2 = new LinkedList<Integer>();

        LinkedList<Integer> output = new LinkedList<Integer>();

        while (list1.size() > 0) {
            LinkedList<Integer> subList1 = new LinkedList<Integer>();

            subList1.add(list1.removeFirst());

            for (Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
                Integer elem = it.next();
                if (subList1.peekLast().compareTo(elem) <= 0) {
                    subList1.addLast(elem);
                    it.remove();
                }
            }

            resultList1 = merge(subList1, resultList1);
        }

        while (list2.size() > 0) {
            LinkedList<Integer> subList2 = new LinkedList<Integer>();

            subList2.add(list2.removeFirst());

            for (Iterator<Integer> it2 = list.iterator(); it2.hasNext(); ) {
                Integer elem = it2.next();
                if (subList2.peekLast().compareTo(elem) <= 0) {
                    subList2.addLast(elem);
                    it2.remove();
                }
            }

            resultList2 = merge(subList2, resultList2);
        }

        output = merge(resultList1, resultList2);

        return output;
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

//        start = System.currentTimeMillis();
//        System.out.println(strandSort(middleList));
//        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));
//
//        start = System.currentTimeMillis();
//        System.out.println(strandSort(bigList));
//        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));
//
//        start = System.currentTimeMillis();
//        System.out.println(strandSort(biggestList));
//        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
