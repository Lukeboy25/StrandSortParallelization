package nl.hva;

import Datasets.DataSet;

import java.util.Iterator;
import java.util.LinkedList;

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

    public static void main(String[] args) {
        DataSet dataset = new DataSet();
        long start = 0;

        start = System.currentTimeMillis();
        System.out.println(strandSort(new LinkedList<Integer>(dataset.smallestDataset)));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(strandSort(new LinkedList<Integer>(dataset.middleDataSet)));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(strandSort(new LinkedList<Integer>(dataset.bigDataSet)));
        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));

//        start = System.currentTimeMillis();
//        System.out.println(strandSort(new LinkedList<Integer>(dataset.biggestDataSet)));
//        System.out.println("Sorting time in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
