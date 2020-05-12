import datasets.TextFileReader;
import nl.hva.Strand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.util.LinkedList;

public class StrandSortUnitTest  {

    private TextFileReader dataset = new TextFileReader();
    private LinkedList<Integer> smallestList = (LinkedList<Integer>) dataset.readFile("src/datasets/smallestDataSet.txt");
    private LinkedList<Integer> middleList = (LinkedList<Integer>) dataset.readFile("src/datasets/middleDataSet.txt");
    private LinkedList<Integer> bigList = (LinkedList<Integer>) dataset.readFile("src/datasets/bigDataSet.txt");
    private LinkedList<Integer> biggestList = (LinkedList<Integer>) dataset.readFile("src/datasets/biggestDataSet.txt");
    private int beginSize = 0;

    public StrandSortUnitTest() throws FileNotFoundException {
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

    @Test
    public void DataSetIsSortedAscending() {
        LinkedList<Integer> resultList = Strand.strandSort(biggestList);
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

    private boolean isArraySortedAscending(LinkedList<Integer> linkedList){
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

