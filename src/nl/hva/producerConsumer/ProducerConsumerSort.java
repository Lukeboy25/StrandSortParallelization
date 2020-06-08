package nl.hva.producerConsumer;

import java.util.LinkedList;

import static helpers.StrandSortHelperMethods.orderList;

public class ProducerConsumerSort implements ProducerConsumerInterface {

    LinkedList<Integer> produceList = new LinkedList<Integer>();

    @Override
    public void produce(LinkedList<Integer> list) throws InterruptedException {
        synchronized (this) {
            while (list.size() == produceList.size()) {
                wait();
            }

            produceList.addAll(list);

            notify();
        }
    }

    public LinkedList<Integer> resultList = new LinkedList<Integer>();

    @Override
    public void consume() throws InterruptedException {
        while (produceList.size() > 0) {
            synchronized (this) {
                while (produceList.size() == 0) {
                    wait();
                }

                resultList = orderList(produceList, resultList);

                notify();
            }
        }
    }
}
