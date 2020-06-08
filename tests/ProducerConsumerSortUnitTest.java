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
        producerConsumer.produce(biggestJavaSortedInputList);
        producerConsumer.consume();

        assertEquals(producerConsumer.resultList, biggestJavaSortedInputList);
    }

    //Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() throws InterruptedException {
        producerConsumer.produce(smallestList);
        producerConsumer.consume();
        assertTrue(isArraySortedAscending(producerConsumer.resultList));
    }

    @Test
    public void MediumSetIsSortedAscending() throws InterruptedException {
        producerConsumer.produce(middleList);
        producerConsumer.consume();
        assertTrue(isArraySortedAscending(producerConsumer.resultList));
    }

    @Test
    public void BigDataSetIsSortedAscending() throws InterruptedException {
        producerConsumer.produce(bigList);
        producerConsumer.consume();
        assertTrue(isArraySortedAscending(producerConsumer.resultList));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() throws InterruptedException {
        producerConsumer.produce(biggestList);
        producerConsumer.consume();
        assertTrue(isArraySortedAscending(producerConsumer.resultList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = smallestList.size();
        producerConsumer.produce(smallestList);
        producerConsumer.consume();
        assertEquals(beginSize, producerConsumer.resultList.size());
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = middleList.size();
        producerConsumer.produce(middleList);
        producerConsumer.consume();
        assertEquals(beginSize, producerConsumer.resultList.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = bigList.size();
        producerConsumer.produce(bigList);
        producerConsumer.consume();
        assertEquals(beginSize, producerConsumer.resultList.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = biggestList.size();
        producerConsumer.produce(biggestList);
        producerConsumer.consume();
        assertEquals(beginSize, producerConsumer.resultList.size());
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