package Datasets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSet {
    public void readFile(String filePath) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filePath));
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (s.hasNext()) {
            if (s.hasNextInt()) {
                list.add(s.nextInt());
            }
        }
    }
}
