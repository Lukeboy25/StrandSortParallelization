package nl.hva;

import datasets.TextFileReader;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class StrandSortVarientExecutioner {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        TextFileReader reader = new TextFileReader();
        LinkedList<Integer> smallestList = reader.readFile("src/datasets/smallestDataSet.txt");
        LinkedList<Integer> middleList =  reader.readFile("src/datasets/middleDataSet.txt");
        LinkedList<Integer> bigList =  reader.readFile("src/datasets/bigDataSet.txt");
        LinkedList<Integer> biggestList = reader.readFile("src/datasets/biggestDataSet.txt");
        long start;

        start = System.currentTimeMillis();
        //Strand.strandSort(smallestList);
        DoubleThreadedStrandSort.strandSort(smallestList);
        //FourThreadedStrandSort.strandSort(smallestList);
        //OctaThreadedStrandSort.strandSort(smallestList);
        System.out.println("Sorting time for 1000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        //Strand.strandSort(middleList);
        DoubleThreadedStrandSort.strandSort(middleList);
        //FourThreadedStrandSort.strandSort(middleList);
        //OctaThreadedStrandSort.strandSort(middleList);
        System.out.println("Sorting time for 10.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        //Strand.strandSort(bigList);
        DoubleThreadedStrandSort.strandSort(bigList);
        //FourThreadedStrandSort.strandSort(bigList);
        //OctaThreadedStrandSort.strandSort(bigList);
        System.out.println("Sorting time for 100.000 in milliseconds: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        //Strand.strandSort(biggestList);
        DoubleThreadedStrandSort.strandSort(biggestList);
        //FourThreadedStrandSort.strandSort(biggestList);
        //OctaThreadedStrandSort.strandSort(biggestList);
        System.out.println("Sorting time for 250.000 in milliseconds: " + (System.currentTimeMillis() - start));
    }
}
