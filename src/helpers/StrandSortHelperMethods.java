package helpers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StrandSortHelperMethods {
    public static LinkedList<Integer> merge(LinkedList<Integer> left, LinkedList<Integer> right) {
        LinkedList<Integer> result = new LinkedList<>();
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

    public static LinkedList<Integer> orderList(List listPart, LinkedList<Integer> resultList) {
        while (listPart.size() > 0) {
            LinkedList<Integer> subList = new LinkedList<>();
            subList.add((Integer) listPart.remove(0));

            for (Iterator<Integer> it = listPart.iterator(); it.hasNext(); ) {
                Integer elem = it.next();
                if (subList.peekLast().compareTo(elem) <= 0) {
                    subList.addLast(elem);
                    it.remove();
                }
            }

            resultList = merge(subList, resultList);
        }

        return resultList;
    }
}
