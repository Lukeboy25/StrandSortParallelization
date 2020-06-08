package nl.hva;

import java.util.Iterator;
import java.util.LinkedList;

import static helpers.StrandSortHelperMethods.merge;

public class ProducerConsumerSort implements ProducerConsumerInterface {

    @Override
    public LinkedList<Integer> produce(LinkedList<Integer> list) throws InterruptedException {
        LinkedList<Integer> produceList = new LinkedList<>();
        synchronized (this) {
            while (list.size() == produceList.size()) {
                wait();
            }

            produceList.addAll(list);
        }

        return produceList;
    }

    @Override
    public LinkedList<Integer> consume(LinkedList<Integer> consumeList) throws InterruptedException {
        LinkedList<Integer> resultList = new LinkedList<Integer>();
        while (consumeList.size() > 0) {
            LinkedList<Integer> sortList = new LinkedList<Integer>();
            synchronized (this) {
                while (consumeList.size() == 0) {
                    wait();
                }

                sortList.add(consumeList.removeFirst());
                for (Iterator<Integer> it = consumeList.iterator(); it.hasNext(); ) {
                    Integer elem = it.next();
                    if (sortList.peekLast().compareTo(elem) <= 0) {
                        sortList.addLast(elem); //same as add(elem) or add(0, elem)
                        it.remove();
                    }
                }

                resultList = merge(sortList, resultList);
            }
        }
        return resultList;
    }
}
