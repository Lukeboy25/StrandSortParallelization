package nl.hva.strandSort;

import datasets.TextFileReader;
import nl.hva.strandSort.DoubleThreadedStrandSort;
import nl.hva.strandSort.FourThreadedStrandSort;
import nl.hva.strandSort.OctaThreadedStrandSort;
import nl.hva.strandSort.StrandSort;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class StrandSortVariantExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        StrandSort originalStrandSortSort = new StrandSort();
        DoubleThreadedStrandSort doubleThreadedStrandSort = new DoubleThreadedStrandSort();
        FourThreadedStrandSort fourThreadedStrandSort = new FourThreadedStrandSort();
        OctaThreadedStrandSort octaThreadedStrandSort = new OctaThreadedStrandSort();

        LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList =  TextFileReader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList =  TextFileReader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
        long start;

        start = System.currentTimeMillis();
        originalStrandSortSort.strandSort(smallestList);
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSortSort.strandSort(middleList);
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSortSort.strandSort(bigList);
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSortSort.strandSort(biggestList);
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
