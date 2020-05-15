package helpers;

import java.util.LinkedList;
import java.util.List;

public class PartitionLinkedList {
    public static <Integer> List<LinkedList<Integer>> partition(LinkedList<Integer> numberList, int maxSize) {
        List<LinkedList<Integer>> res = new LinkedList<>();
        LinkedList<Integer> internal = new LinkedList<>();
        for (Integer number : numberList) {
            internal.add(number);
            if (internal.size() == maxSize) {
                res.add(internal);
                internal = new LinkedList<>();
            }
        }
        if (internal.isEmpty() == false) {
            res.add(internal);
        }
        return res;
    }
}
