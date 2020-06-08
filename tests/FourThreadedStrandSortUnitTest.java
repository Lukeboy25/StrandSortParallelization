import datasets.TextFileReader;
import nl.hva.FourThreadedStrandSort;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class FourThreadedStrandSortUnitTest {
    FourThreadedStrandSort fourThreadedStrandSort = new FourThreadedStrandSort();
    private LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = TextFileReader.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = TextFileReader.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public FourThreadedStrandSortUnitTest() throws FileNotFoundException {}

    @Test
    public void StrandSortResultIsSameAsDefaultCollectionSort() {
        Collections.sort(biggestJavaSortedInputList);
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(biggestList);

        assertEquals(resultList.equals(biggestJavaSortedInputList), true);
    }

    //Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(smallestList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void MediumSetIsSortedAscending() {
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(middleList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void BigDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(bigList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void BiggestDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(biggestList);
        assertTrue(isArraySortedAscending(resultList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() {
        beginSize = smallestList.size();
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(smallestList);
        assertEquals(resultList.size(), beginSize);
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() {
        beginSize = middleList.size();
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(middleList);
        assertEquals(beginSize,resultList.size());
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() {
        beginSize = bigList.size();
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(bigList);
        assertEquals(beginSize,resultList.size());
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() {
        beginSize = biggestList.size();
        LinkedList<Integer> resultList = fourThreadedStrandSort.strandSort(biggestList);
        assertEquals(beginSize,resultList.size());
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
