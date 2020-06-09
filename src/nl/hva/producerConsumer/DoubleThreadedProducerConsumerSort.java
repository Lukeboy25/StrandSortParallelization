package nl.hva.producerConsumer;

import java.util.LinkedList;
import java.util.List;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class DoubleThreadedProducerConsumerSort implements ProducerConsumerInterface {
    private final int NUMBER_OF_THREADS = 2;

    private LinkedList<Integer> consumeList = new LinkedList<Integer>();
    private LinkedList<Integer> temporarilyList = new LinkedList<Integer>();

    @Override
    public void starter(LinkedList<Integer> produceList) throws InterruptedException {
        List<LinkedList<Integer>> partitions = partition(produceList, produceList.size() / NUMBER_OF_THREADS);

        Thread firstThread = new Thread(() -> {
            try {
                produce(partitions.get(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        firstThread.start();

        Thread secondThread = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        secondThread.start();

        firstThread.join();
        secondThread.join();

        firstThread = new Thread(() -> {
            try {
                produce(partitions.get(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        firstThread.start();

        secondThread = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        secondThread.start();

        firstThread.join();
        secondThread.join();

    }

    @Override
    public void produce(LinkedList<Integer> produceList) throws InterruptedException {
        synchronized (this) {
            while (consumeList.size() == produceList.size()) {
                wait();
            }

            consumeList.addAll(produceList);

            // notifies the consumer thread that it can start consuming
            notify();
        }
    }

    public LinkedList<Integer> resultList = new LinkedList<Integer>();

    @Override
    public void consume() throws InterruptedException {
        synchronized (this) {
            while (consumeList.isEmpty()) {
                wait();
            }

            temporarilyList = orderList(consumeList, temporarilyList);
            resultList = merge(temporarilyList, resultList);
            temporarilyList.clear();

            notify();
        }
    }
}
