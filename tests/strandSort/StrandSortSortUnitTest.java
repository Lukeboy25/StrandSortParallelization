package strandSort;

import datasets.TextFileReader;
import nl.hva.strandSort.StrandSort;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class StrandSortSortUnitTest {

    StrandSort strandSort = new StrandSort();
    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public StrandSortSortUnitTest() throws FileNotFoundException {}

    @Test
    public void StrandSortResultIsSameAsDefaultCollectionSort() {
        Collections.sort(biggestJavaSortedInputList);
        LinkedList<Integer> resultList = strandSort.strandSort(biggestList);

        assertEquals(resultList, biggestJavaSortedInputList);
    }

    //Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = strandSort.strandSort(smallestList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void MediumSetIsSortedAscending() {
        LinkedList<Integer> resultList = strandSort.strandSort(middleList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void BigDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = strandSort.strandSort(bigList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = strandSort.strandSort(biggestList);
        assertTrue(isArraySortedAscending(resultList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() {
        beginSize = smallestList.size();
        LinkedList<Integer> resultList = strandSort.strandSort(smallestList);
        assertEquals(beginSize, resultList.size());
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() {
        beginSize = middleList.size();
        LinkedList<Integer> resultList = strandSort.strandSort(middleList);
        assertEquals(beginSize, resultList.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() {
        beginSize = bigList.size();
        LinkedList<Integer> resultList = strandSort.strandSort(bigList);
        assertEquals(beginSize, resultList.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() {
        beginSize = biggestList.size();
        LinkedList<Integer> resultList = strandSort.strandSort(biggestList);
        assertEquals(beginSize, resultList.size());
    }

    private boolean isArraySortedAscending(LinkedList<Integer> linkedList) {
        Integer[] array = new Integer[linkedList.size()];
        array = linkedList.toArray(array);

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }

        return true;
    }
}

