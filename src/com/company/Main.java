package com.company;

public class Main {

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");
        grammar.productions.get("A").forEach(prod->
        {
            Item itm=new Item(grammar,"A",prod);
            System.out.println(itm.moveDotAfter(itm.prod.get(1)));
        });
    }
}
