import datasets.TextFileReader;
import nl.hva.producerConsumer.FourThreadedProducerConsumerSort;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FourThreadedProducerConsumerSortUnitTest {

    FourThreadedProducerConsumerSort fourThreadedProducerConsumer = new FourThreadedProducerConsumerSort();
    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);
    public FourThreadedProducerConsumerSortUnitTest() throws FileNotFoundException {}

    @Test
    public void StrandSortResultIsSameAsDefaultCollectionSort() throws InterruptedException {
        Collections.sort(biggestJavaSortedInputList);
        fourThreadedProducerConsumer.produce(biggestJavaSortedInputList);
        fourThreadedProducerConsumer.consume();

        assertEquals(FourThreadedProducerConsumerSort.result, biggestJavaSortedInputList);
    }

    // Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() throws InterruptedException {
        fourThreadedProducerConsumer.produce(smallestList);
        fourThreadedProducerConsumer.consume();
        assertTrue(isArraySortedAscending(FourThreadedProducerConsumerSort.result));
    }

    @Test
    public void MediumSetIsSortedAscending() throws InterruptedException {
        fourThreadedProducerConsumer.produce(middleList);
        fourThreadedProducerConsumer.consume();
        assertTrue(isArraySortedAscending(FourThreadedProducerConsumerSort.result));
    }

    @Test
    public void BigDataSetIsSortedAscending() throws InterruptedException {
        fourThreadedProducerConsumer.produce(bigList);
        fourThreadedProducerConsumer.consume();
        assertTrue(isArraySortedAscending(FourThreadedProducerConsumerSort.result));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() throws InterruptedException {
        fourThreadedProducerConsumer.produce(biggestList);
        fourThreadedProducerConsumer.consume();
        assertTrue(isArraySortedAscending(FourThreadedProducerConsumerSort.result));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = smallestList.size();
        fourThreadedProducerConsumer.produce(smallestList);
        fourThreadedProducerConsumer.consume();
        assertEquals(beginSize, FourThreadedProducerConsumerSort.result.size());
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = middleList.size();
        fourThreadedProducerConsumer.produce(middleList);
        fourThreadedProducerConsumer.consume();
        assertEquals(beginSize, FourThreadedProducerConsumerSort.result.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = bigList.size();
        fourThreadedProducerConsumer.produce(bigList);
        fourThreadedProducerConsumer.consume();
        assertEquals(beginSize, FourThreadedProducerConsumerSort.result.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() throws InterruptedException {
        beginSize = biggestList.size();
        fourThreadedProducerConsumer.produce(biggestList);
        fourThreadedProducerConsumer.consume();
        assertEquals(beginSize, FourThreadedProducerConsumerSort.result.size());
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
