package helpers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static helpers.PartitionLinkedList.partition;
import static helpers.StrandSortHelperMethods.merge;
import static helpers.StrandSortHelperMethods.orderList;

public class ProducerConsumerHelper {
    private volatile LinkedList<Integer> consumeList = new LinkedList<Integer>();
    private volatile LinkedList<Integer> temporaryList = new LinkedList<Integer>();

    public void starter(LinkedList<Integer> produceList, int amountOfThreads) throws InterruptedException {
        List<LinkedList<Integer>> partitions = partition(produceList, produceList.size() / amountOfThreads);

        ArrayList<Thread> produceTasks = new ArrayList<>(amountOfThreads);
        ArrayList<Thread> consumeTasks = new ArrayList<>(amountOfThreads);

        for (int i = 0; i < amountOfThreads; i++) {
            LinkedList<Integer> listPart = partitions.get(i);

            produceTasks.add(new Thread(() -> {
                try {
                    produce(listPart);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
            produceTasks.get(i).start();

            consumeTasks.add(new Thread(() -> {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
            consumeTasks.get(i).start();
        }

        for (int i = 0; i < amountOfThreads; i++) {
            produceTasks.get(i).join();
            consumeTasks.get(i).join();
        }
    }

    private void produce(LinkedList<Integer> produceList) throws InterruptedException {
        synchronized (this) {
            while (consumeList.size() == produceList.size()) {
                wait();
            }

            consumeList.addAll(produceList);

            // notifies the consumer thread that it can start consuming
            notifyAll();
        }
    }

    public volatile LinkedList<Integer> resultList = new LinkedList<Integer>();

    private void consume() throws InterruptedException {
        synchronized (this) {
            while (consumeList.isEmpty()) {
                wait();
            }

            temporaryList = orderList(consumeList, temporaryList);
            resultList = merge(temporaryList, resultList);
            temporaryList.clear();

            notify();
        }
    }
}
