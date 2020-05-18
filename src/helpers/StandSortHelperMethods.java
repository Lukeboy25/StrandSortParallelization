package helpers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class StandSortHelperMethods {
    public static LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right) {
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

    public static void orderList(LinkedList<Integer> listPart, AtomicReference<LinkedList> resultList) {
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
}
