import datasets.TextFileReader;
import nl.hva.Strand;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class StrandSortUnitTest {

    private TextFileReader dataset = new TextFileReader();
    private LinkedList<Integer> smallestList = dataset.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = dataset.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = dataset.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = dataset.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    private LinkedList<Integer> biggestJavaSortedInputList = new LinkedList<>(biggestList);

    public StrandSortUnitTest() throws FileNotFoundException {}

    @Test
    public void DataSetIsSortedAscending() {
        Collections.sort(biggestJavaSortedInputList);
        LinkedList<Integer> resultList = Strand.strandSort(biggestList);
        if (isArraySortedAscending(resultList) && resultList.equals(biggestJavaSortedInputList)) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    //Ascending tests
    @Test
    public void SmallDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = Strand.strandSort(smallestList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void MediumSetIsSortedAscending() {
        LinkedList<Integer> resultList = Strand.strandSort(middleList);
        assertTrue(isArraySortedAscending(resultList));
    }

    @Test
    public void BigDataSetIsSortedAscending() {
        LinkedList<Integer> resultList = Strand.strandSort(bigList);
        assertTrue(isArraySortedAscending(resultList));
    }

    // Size tests
    @Test
    public void SmallDataResultIsSameSizeAsStartList() {
        beginSize = smallestList.size();
        LinkedList<Integer> resultList = Strand.strandSort(smallestList);
        assertEquals(resultList.size(), beginSize);
    }

    @Test
    public void MediumDataResultIsSameSizeAsStartList() {
        beginSize = middleList.size();
        LinkedList<Integer> resultList = Strand.strandSort(middleList);
        assertEquals(resultList.size(), beginSize);
    }

    @Test
    public void BigDataResultIsSameSizeAsStartList() {
        beginSize = bigList.size();
        LinkedList<Integer> resultList = Strand.strandSort(bigList);
        assertEquals(resultList.size(), beginSize);
    }

    @Test
    public void BiggestDataResultIsSameSizeAsStartList() {
        beginSize = biggestList.size();
        LinkedList<Integer> resultList = Strand.strandSort(biggestList);
        assertEquals(resultList.size(), beginSize);
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

