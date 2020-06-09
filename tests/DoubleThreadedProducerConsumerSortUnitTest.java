import datasets.TextFileReader;
import nl.hva.producerConsumer.DoubleThreadedProducerConsumerSort;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoubleThreadedProducerConsumerSortUnitTest {

    DoubleThreadedProducerConsumerSort doubleThreadedProducerConsumer = new DoubleThreadedProducerConsumerSort();
    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public DoubleThreadedProducerConsumerSortUnitTest() throws FileNotFoundException {}

    @Test
    public void StrandSortResultIsSameAsDefaultCollectionSort() throws InterruptedException {
        Collections.sort(biggestJavaSortedInputList);
        doubleThreadedProducerConsumer.starter(biggestJavaSortedInputList);

        assertEquals(doubleThreadedProducerConsumer.resultList, biggestJavaSortedInputList);
    }

    // Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() throws InterruptedException {
        doubleThreadedProducerConsumer.starter(smallestList);
        assertTrue(isArraySortedAscending(doubleThreadedProducerConsumer.resultList));
    }

    @Test
    public void MediumSetIsSortedAscending() throws InterruptedException {
        doubleThreadedProducerConsumer.starter(middleList);
        assertTrue(isArraySortedAscending(doubleThreadedProducerConsumer.resultList));
    }

    @Test
    public void BigDataSetIsSortedAscending() throws InterruptedException {
        doubleThreadedProducerConsumer.starter(bigList);
        assertTrue(isArraySortedAscending(doubleThreadedProducerConsumer.resultList));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() throws InterruptedException {
        doubleThreadedProducerConsumer.starter(biggestList);
        assertTrue(isArraySortedAscending(doubleThreadedProducerConsumer.resultList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = smallestList.size();
        doubleThreadedProducerConsumer.starter(smallestList);
        assertEquals(beginSize, doubleThreadedProducerConsumer.resultList.size());
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = middleList.size();
        doubleThreadedProducerConsumer.starter(middleList);
        assertEquals(beginSize, doubleThreadedProducerConsumer.resultList.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = bigList.size();
        doubleThreadedProducerConsumer.starter(bigList);
        assertEquals(beginSize, doubleThreadedProducerConsumer.resultList.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = biggestList.size();
        doubleThreadedProducerConsumer.starter(biggestList);
        assertEquals(beginSize, doubleThreadedProducerConsumer.resultList.size());
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