package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static List<String> readPif(String filename) {
        try {
            File pifFile = new File(filename);
            Scanner reader = new Scanner(pifFile);

            List<String> ret = new LinkedList<>();

            while (reader.hasNextLine()) {
                ret.add(reader.nextLine().strip().split(" ")[0]);
            }
            reader.close();

            return ret;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g2.txt");
        grammar.Enhance();
        List<Set<Item>> states=Parser.canonicalCollection(grammar);
        LRtable table=new LRtable(states,grammar);
        table.printTable();
        List<String> w = readPif("PIF.out");
        System.out.println(w);
        System.out.println(Parser.parse(grammar,w,table));
    }
}
