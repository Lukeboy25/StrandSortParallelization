package nl.hva.producerConsumer;

import java.util.LinkedList;
import java.util.List;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class FourThreadedProducerConsumerSort implements ProducerConsumerInterface {

    private final int NUMBER_OF_THREADS = 4;

    private LinkedList<Integer> firstProduceList = new LinkedList<>();
    private LinkedList<Integer> secondProduceList = new LinkedList<>();
    private LinkedList<Integer> thirdProduceList = new LinkedList<>();
    private LinkedList<Integer> fourthProduceList = new LinkedList<>();

    private LinkedList<Integer> firstResultList = new LinkedList<>();
    private LinkedList<Integer> secondResultList = new LinkedList<>();
    private LinkedList<Integer> thirdResultList = new LinkedList<>();
    private LinkedList<Integer> fourthResultList = new LinkedList<>();

    private LinkedList<Integer> resultThreadOneTwo = new LinkedList<>();
    private LinkedList<Integer> resultThreadThreeFour = new LinkedList<>();
    public static LinkedList<Integer> result = new LinkedList<>();

    @Override
    public void produce(LinkedList<Integer> produceList) throws InterruptedException {

        List<LinkedList<Integer>> partitions = partition(produceList, produceList.size() / NUMBER_OF_THREADS);
        LinkedList<Integer> firstPart = partitions.get(0);
        LinkedList<Integer> secondPart = partitions.get(1);
        LinkedList<Integer> thirdPart = partitions.get(2);
        LinkedList<Integer> fourthPart = partitions.get(3);

        while (firstProduceList.size() == (produceList.size() / NUMBER_OF_THREADS) && secondProduceList.size() == (produceList.size() / NUMBER_OF_THREADS)
                && thirdProduceList.size() == (produceList.size() / NUMBER_OF_THREADS) && fourthProduceList.size() == (produceList.size() / NUMBER_OF_THREADS)) {
            wait();
        }

        synchronized (this) {
            Thread firstThread = new Thread(() -> {
                firstProduceList = firstPart;
            });
            firstThread.start();
            firstThread.join();

            notify();

            Thread secondThread = new Thread(() -> {
                secondProduceList = secondPart;
            });
            secondThread.start();
            secondThread.join();

            notify();

            Thread thirdThread = new Thread(() -> {
                thirdProduceList = thirdPart;
            });
            thirdThread.start();
            thirdThread.join();

            notify();

            Thread fourthThread = new Thread(() -> {
                fourthProduceList = fourthPart;
            });
            fourthThread.start();
            fourthThread.join();

            notify();
        }
    }

    @Override
    public void consume() throws InterruptedException {
        while (firstProduceList.size() == 0) {
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

            notify();

            firstThread = new Thread(() -> {
                resultThreadOneTwo = merge(firstResultList, secondResultList);
            });
            firstThread.start();

            Thread thirdThread = new Thread(() -> {
                thirdResultList = orderList(thirdProduceList, thirdResultList);
            });
            thirdThread.start();

            Thread fourthThread = new Thread(() -> {
                fourthResultList = orderList(fourthProduceList, fourthResultList);
            });
            fourthThread.start();

            thirdThread.join();
            fourthThread.join();

            notify();

            thirdThread = new Thread(() -> {
                resultThreadThreeFour = merge(thirdResultList, fourthResultList);
            });
            thirdThread.start();

            firstThread.join();
            thirdThread.join();

            notify();

            result = merge(resultThreadOneTwo, resultThreadThreeFour);
        }
    }
}
