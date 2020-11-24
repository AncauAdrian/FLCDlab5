package com.company;

public class Main {

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.readFromFile("g1.txt");

        grammar.printMenu();
    }
}
