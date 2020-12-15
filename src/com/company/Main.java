package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void output(String filename, Grammar grammar, String parserOutput) {
        try {
            File out2 = new File(filename);
            FileWriter writer = new FileWriter(out2);

            String output = "";

            output = grammar.productions.entrySet()
                    .stream()
                    .map(entity -> {
                        String res = entity.getKey() + "->";
                        for (Pair<Integer, String> prod : entity.getValue()) {
                            res = res + "(" + prod.first + ")" + prod.second + "|";
                        }
                        return res.substring(0, res.length() - 1) + " ";
                    })
                    .reduce("", (str, entity) -> str + entity);

            writer.write(output + "\n" + parserOutput);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        try {
            output("out1.txt", grammar, Parser.parse(grammar, w, table).toString());
        }
        catch (Exception e) {
            output("out1.txt", grammar, "Parser error: " + e.toString());
        }

        grammar = new Grammar();
        grammar.readFromFile("g2.txt");
        grammar.Enhance();
        states = Parser.canonicalCollection(grammar);
        table = new LRtable(states, grammar);
        table.printTable();
        w = readPif("PIF.out");
        System.out.println(w);

        try {
            output("out2.txt", grammar, Parser.parse(grammar, w, table).toString());
        }
        catch (Exception e) {
            output("out2.txt", grammar, "Parser error: " + e.toString());
        }

    }
}
