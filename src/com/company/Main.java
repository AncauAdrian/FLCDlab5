package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");
        grammar.Enhance();
        List<Set<Item>> states=Parser.canonicalCollection(grammar);
        LRtable table=new LRtable(states,grammar);
        table.printTable();
        List<String> w=new ArrayList<>();
        w.add("a");
        w.add("b");
        w.add("b");
        w.add("c");
        System.out.println(Parser.parse(grammar,w,table));
    }
}
