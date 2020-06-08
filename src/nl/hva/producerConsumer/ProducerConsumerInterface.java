package nl.hva.producerConsumer;

import java.util.LinkedList;

public interface ProducerConsumerInterface {
   void produce(LinkedList<Integer> produceList) throws InterruptedException;

    void consume() throws InterruptedException;
}