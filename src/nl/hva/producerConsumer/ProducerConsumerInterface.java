package nl.hva.producerConsumer;

import java.util.LinkedList;

public interface ProducerConsumerInterface {

    void starter(LinkedList<Integer> produceList) throws InterruptedException;

    void produce(LinkedList<Integer> produceList) throws InterruptedException;

   void consume() throws InterruptedException;
}