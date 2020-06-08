package nl.hva.producerConsumer;

import java.util.Iterator;
import java.util.LinkedList;

import static helpers.StrandSortHelperMethods.merge;

public class ProducerConsumerSort implements ProducerConsumerInterface {

    LinkedList<Integer> produceList = new LinkedList<>();

    @Override
    public void produce(LinkedList<Integer> list) throws InterruptedException {
        synchronized (this) {
            while (list.size() == produceList.size()) {
                wait();
            }

            produceList.addAll(list);
        }
    }

    @Override
    public void consume() throws InterruptedException {
        LinkedList<Integer> resultList = new LinkedList<Integer>();
        while (produceList.size() > 0) {
            LinkedList<Integer> sortList = new LinkedList<Integer>();
            synchronized (this) {
                while (produceList.size() == 0) {
                    wait();
                }

                sortList.add(produceList.removeFirst());
                for (Iterator<Integer> it = produceList.iterator(); it.hasNext(); ) {
                    Integer elem = it.next();
                    if (sortList.peekLast().compareTo(elem) <= 0) {
                        sortList.addLast(elem); //same as add(elem) or add(0, elem)
                        it.remove();
                    }
                }

                resultList = merge(sortList, resultList);
            }
        }
    }
}
