package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class StrandSortVarientExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Strand originalStrandSort = new Strand();
        DoubleThreadedStrandSort doubleThreadedStrandSort = new DoubleThreadedStrandSort();
        FourThreadedStrandSort fourThreadedStrandSort = new FourThreadedStrandSort();
        OctaThreadedStrandSort octaThreadedStrandSort = new OctaThreadedStrandSort();

        LinkedList<Integer> smallestList = TextFileReader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList =  TextFileReader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList =  TextFileReader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = TextFileReader.readFile("src/datasets/biggestDataSet.txt");
        long start;

        start = System.currentTimeMillis();
        originalStrandSort.strandSort(smallestList);
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSort.strandSort(middleList);
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSort.strandSort(bigList);
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        originalStrandSort.strandSort(biggestList);
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
