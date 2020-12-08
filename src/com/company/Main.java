package com.company;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g2.txt");
        grammar.Enhance();
        List<Set<Item>> states=Parser.canonicalCollection(grammar);
        LRtable table=new LRtable(states,grammar);
        table.printTable();
    }
}
