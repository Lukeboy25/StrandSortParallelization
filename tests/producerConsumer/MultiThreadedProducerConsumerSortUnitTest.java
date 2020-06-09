package producerConsumer;

import datasets.TextFileReader;
import helpers.ProducerConsumerHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiThreadedProducerConsumerSortUnitTest {

    ProducerConsumerHelper producerConsumerHelper = new ProducerConsumerHelper();

    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public MultiThreadedProducerConsumerSortUnitTest() throws FileNotFoundException {}

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void StrandSortResultIsSameAsDefaultCollectionSort(int amountOfThreads) throws InterruptedException {
        Collections.sort(biggestJavaSortedInputList);
        producerConsumerHelper.starter(biggestJavaSortedInputList, amountOfThreads);

        assertEquals(producerConsumerHelper.resultList, biggestJavaSortedInputList);
    }

    // Ascending tests
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void SmallDataSetIsSortedAscending(int amountOfThreads) throws InterruptedException {
        producerConsumerHelper.starter(smallestList, amountOfThreads);
        assertTrue(isArraySortedAscending(producerConsumerHelper.resultList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void MediumSetIsSortedAscending(int amountOfThreads) throws InterruptedException {
        producerConsumerHelper.starter(middleList, amountOfThreads);
        assertTrue(isArraySortedAscending(producerConsumerHelper.resultList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void BigDataSetIsSortedAscending(int amountOfThreads) throws InterruptedException {
        producerConsumerHelper.starter(bigList, amountOfThreads);
        assertTrue(isArraySortedAscending(producerConsumerHelper.resultList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void BiggestDataSetIsSortedAscending(int amountOfThreads) throws InterruptedException {
        producerConsumerHelper.starter(biggestList, amountOfThreads);
        assertTrue(isArraySortedAscending(producerConsumerHelper.resultList));
    }

    // Size tests
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void SmallDataResultIsSameSizeAsStartList(int amountOfThreads) throws InterruptedException {
        beginSize = smallestList.size();
        producerConsumerHelper.starter(smallestList, amountOfThreads);
        assertEquals(beginSize, producerConsumerHelper.resultList.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void MediumDataResultIsSameSizeAsStartList(int amountOfThreads) throws InterruptedException {
        beginSize = middleList.size();
        producerConsumerHelper.starter(middleList, amountOfThreads);
        assertEquals(beginSize, producerConsumerHelper.resultList.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void BigDataResultIsSameSizeAsStartList(int amountOfThreads) throws InterruptedException {
        beginSize = bigList.size();
        producerConsumerHelper.starter(bigList, amountOfThreads);
        assertEquals(beginSize, producerConsumerHelper.resultList.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    public void BiggestDataResultIsSameSizeAsStartList(int amountOfThreads) throws InterruptedException {
        beginSize = biggestList.size();
        producerConsumerHelper.starter(biggestList, amountOfThreads);
        assertEquals(beginSize, producerConsumerHelper.resultList.size());
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