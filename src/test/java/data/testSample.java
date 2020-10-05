package data;

import java.io.IOException;
import java.util.ArrayList;

public class testSample {
    public static void main(String[] args) throws IOException {
        DataDriven data = new DataDriven();
        ArrayList<String> list = data.getData("Books","AddBook");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
    }
}
