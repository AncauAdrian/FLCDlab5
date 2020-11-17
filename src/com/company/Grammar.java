package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    private final List<String> nonTerminals = new LinkedList<>();
    private final List<String> terminals = new LinkedList<>();
    private final HashMap<String, List<String>> productions = new HashMap<>();
    private String startingSymbol;

    private void readFromFile(String path) {
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);

            String line = reader.nextLine();

            // non terminals are on the first line, separated by comma
            nonTerminals.addAll(Arrays.asList(line.split(",")));
            line = reader.nextLine().replaceAll("\\s", "");

            startingSymbol = nonTerminals.get(0);

            // terminals are on the second line, separated by comma
            terminals.addAll(Arrays.asList(line.split(",")));

            // Read productions
            while(reader.hasNextLine()) {
                line = reader.nextLine().replaceAll("\\s", "");
                String[] components = line.split("[>|]");
                List<String> list = new LinkedList<>(Arrays.asList(components));
                list.remove(0);

                productions.put(components[0], list);
            }


            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + path);
            e.printStackTrace();
        }
    }
}
