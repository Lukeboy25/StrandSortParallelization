import datasets.TextFileReader;
import nl.hva.producerConsumer.ProducerConsumerSort;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ProducerConsumerSortUnitTest {

    ProducerConsumerSort producerConsumer = new ProducerConsumerSort();
    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public ProducerConsumerSortUnitTest() throws FileNotFoundException {}

    @Test
    public void StrandSortResultIsSameAsDefaultCollectionSort() throws InterruptedException {
        Collections.sort(biggestJavaSortedInputList);
        LinkedList<Integer> produceList = producerConsumer.produce(biggestJavaSortedInputList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);

        assertEquals(consumeList, biggestJavaSortedInputList);
    }

    //Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() throws InterruptedException {
        LinkedList<Integer> produceList = producerConsumer.produce(smallestList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertTrue(isArraySortedAscending(consumeList));
    }

    @Test
    public void MediumSetIsSortedAscending() throws InterruptedException {
        LinkedList<Integer> produceList = producerConsumer.produce(middleList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertTrue(isArraySortedAscending(consumeList));
    }

    @Test
    public void BigDataSetIsSortedAscending() throws InterruptedException {
        LinkedList<Integer> produceList = producerConsumer.produce(bigList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertTrue(isArraySortedAscending(consumeList));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() throws InterruptedException {
        LinkedList<Integer> produceList = producerConsumer.produce(biggestList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertTrue(isArraySortedAscending(consumeList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = smallestList.size();
        LinkedList<Integer> produceList = producerConsumer.produce(smallestList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertEquals(beginSize, consumeList.size());
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = middleList.size();
        LinkedList<Integer> produceList = producerConsumer.produce(middleList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertEquals(beginSize, consumeList.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = bigList.size();
        LinkedList<Integer> produceList = producerConsumer.produce(bigList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertEquals(beginSize, consumeList.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = biggestList.size();
        LinkedList<Integer> produceList = producerConsumer.produce(biggestList);
        LinkedList<Integer> consumeList = producerConsumer.consume(produceList);
        assertEquals(beginSize, consumeList.size());
    }

    private boolean isArraySortedAscending(LinkedList<Integer> linkedList) {
        Integer[] array = new Integer[linkedList.size()];
        array = linkedList.toArray(array);

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false; //return when the current element is bigger than te next element
            }
        }

        return true;
    }
}