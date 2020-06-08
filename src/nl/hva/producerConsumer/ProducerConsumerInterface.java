package nl.hva.producerConsumer;

import java.util.LinkedList;

public interface ProducerConsumerInterface {
    LinkedList<Integer> produce(LinkedList<Integer> produceList) throws InterruptedException;

    LinkedList<Integer> consume(LinkedList<Integer> consumeList) throws InterruptedException;
}