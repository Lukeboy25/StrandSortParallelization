package nl.hva;

import helpers.StrandSortHelperMethods;

import java.util.Iterator;
import java.util.LinkedList;

public class Strand {
    // note: the input list is destroyed
    public static LinkedList<Integer> strandSort(LinkedList<Integer> list) {
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
            result = StrandSortHelperMethods.merge(sorted, result);
        }
        return result;
    }
}
