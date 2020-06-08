package nl.hva.producerConsumer;

import java.util.LinkedList;
import java.util.List;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class DoubleThreadedProducerConsumerSort implements ProducerConsumerInterface {
    private final int NUMBER_OF_THREADS = 2;

    private LinkedList<Integer> firstProduceList = new LinkedList<Integer>();
    private LinkedList<Integer> secondProduceList = new LinkedList<Integer>();

    private LinkedList<Integer> firstResultList = new LinkedList<Integer>();
    private LinkedList<Integer> secondResultList = new LinkedList<Integer>();

    @Override
    public void produce(LinkedList<Integer> produceList) throws InterruptedException {
        List<LinkedList<Integer>> partitions = partition(produceList, produceList.size() / NUMBER_OF_THREADS);

        while (firstProduceList.size() == (produceList.size() / NUMBER_OF_THREADS) && secondProduceList.size() == (produceList.size() / NUMBER_OF_THREADS)) {
            wait();
        }

        synchronized (this) {
            Thread firstThread = new Thread(() -> {
                firstProduceList = partitions.get(0);
            });
            firstThread.start();
            firstThread.join();

            notify();

            Thread secondThread = new Thread(() -> {
                secondProduceList = partitions.get(1);
            });
            secondThread.start();

            secondThread.join();
        }
    }

    public LinkedList<Integer> resultList = new LinkedList<Integer>();

    @Override
    public void consume() throws InterruptedException {
        while (firstProduceList.size() == 0 && secondProduceList.size() == 0) {
            wait();
        }

        synchronized (this) {
            Thread firstThread = new Thread(() -> {
                firstResultList = orderList(firstProduceList, firstResultList);
            });
            firstThread.start();
            firstThread.join();

            notify();

            Thread secondThread = new Thread(() -> {
                secondResultList = orderList(secondProduceList, secondResultList);
            });

            secondThread.start();
            secondThread.join();

            resultList = merge(firstResultList, secondResultList);
        }
    }
}
