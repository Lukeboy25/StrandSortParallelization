package datasets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TextFileReader {
    public static LinkedList<Integer> readFile(String filePath) throws FileNotFoundException {
        Scanner s = new Scanner(new File(filePath));
        LinkedList<Integer> list = new LinkedList<Integer>();
        while (s.hasNext()) {
            if (s.hasNextInt()) {
                list.add(s.nextInt());
            }
        }
        return list;
    }
}
