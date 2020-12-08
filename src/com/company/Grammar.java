package com.company;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    public final Set<String> nonTerminals = new HashSet<>();
    public final Set<String> terminals = new HashSet<>();
    public final HashMap<String, Set<Pair<Integer,String>>> productions = new HashMap<>(); //Map<Symbol,List of productions(prod string and nr)>
    public String startingSymbol;

    public void readFromFile(String path) {
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);

            // non terminals are on the first line, separated by comma
            String line = reader.nextLine();
            String[] terms = line.split(",");
            startingSymbol = terms[0];
            nonTerminals.addAll(Arrays.asList(terms));

            line = reader.nextLine().replaceAll("\\s", "");
            // terminals are on the second line, separated by comma
            terminals.addAll(Arrays.asList(line.split(",")));

            // Read productions
            int prodNR=2;
            while(reader.hasNextLine()) {

                line = reader.nextLine().replaceAll("\\s", "");
                String[] components = line.split("[:|]");
                Set<Pair<Integer,String>> list = new HashSet<>();

                for (String component : components) {
                    if(!component.equals(components[0])){
                        list.add(new Pair<>(prodNR,component));prodNR++;}
                }

                productions.put(components[0], list);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + path);
            e.printStackTrace();
        }
    }
    public Set<Pair<Integer,String>> getProd(String elem){
        return productions.get(elem);
    }
    public Grammar Enhance(){
        Set<Pair<Integer,String>> prod=new HashSet<>();
        prod.add(new Pair<>(1,startingSymbol));
        startingSymbol=startingSymbol+"'";
        nonTerminals.add(startingSymbol);
        productions.put(startingSymbol,prod);
        return this;
    }

    public void printMenu(){
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("0.Terminate | 1.Nonterminals| 2.Terminals | 3.Productions | 4.Production for nonterminal");
            int x=scan.nextInt();
            switch(x){
                case 0:
                    scan.close();
                    return;
                case 1:
                    System.out.println(nonTerminals.toString());
                    break;
                case 2:
                    System.out.println(terminals.toString());
                    break;
                case 3:
                    productions.forEach((key, value) ->{
                        String res=key+"->";
                        for (Pair<Integer,String> prod:value) {
                            res=res+"("+prod.getKey()+")"+prod.getValue()+"|";
                        };
                        System.out.println(res.substring(0,res.length()-1));
                    });
                    break;
                case 4:
                    scan.nextLine();
                    String nonterminal=scan.nextLine().strip();
                    if(!nonTerminals.contains(nonterminal)){
                        System.out.println("Not a nonterminal");
                        break;
                    }
                    String res=nonterminal+"->";
                    for (Pair<Integer,String> prod:productions.get(nonterminal))
                            res=res+"("+prod.getKey()+")"+prod.getValue()+"|";
                    System.out.println(res.substring(0,res.length()-1));
                    break;
            }
        }
    }

}
