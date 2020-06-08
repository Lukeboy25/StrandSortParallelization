package nl.hva.producerConsumer;

import java.util.LinkedList;

public interface ProducerConsumerInterface {
    LinkedList<Integer> produce(LinkedList<Integer> t) throws InterruptedException;

    LinkedList<Integer> consume(LinkedList<Integer> t) throws InterruptedException;
}