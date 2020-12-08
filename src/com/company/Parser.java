package com.company;

import javafx.util.Pair;

import java.util.*;

public class Parser {
    private static Set<Item> closure(Set<Item> items,Grammar g) {
        Set<Item> C = new HashSet<>(items);
        Set<Item> visited=new HashSet<>();
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<Item> C1 = new HashSet<>(items);
            C1.removeAll(visited); //foreach on C breaks for g2 |ConcurrentModificationException|
            for (Item itm : C1) {
                if(!visited.contains(itm)){
                    visited.add(itm);
                    int pos = itm.dotPos + 1;
                    if (pos < itm.prod.size()) {
                        String symbol = itm.prod.get(itm.dotPos + 1);
                        if(g.productions.get(symbol)!=null){
                            for (Pair<Integer,String> production : g.productions.get(symbol)) {
                                Item newItm = new Item(g, symbol, production.getValue(),production.getKey());
                                if (!C.contains(newItm)) {
                                    changed = true;
                                    C.add(newItm);
                                }
                            }
                        }

                    }
                }
            }
        }
        return C;
    }

    public static Set<Item> goTo(Set<Item> state,String X,Grammar g) {
        Set<Item> s=new HashSet<>();
        state.forEach(item -> {
            if(X.equals(item.afterDot()))
                s.add(item.incrementDot());
        });
        if(s.isEmpty())
            return null;
        return closure(s,g);
    }

    public static List<Set<Item>> canonicalCollection(Grammar g) {
        List<Set<Item>> C=new ArrayList<>();
        Set<Item> s0 = new HashSet<>(closure(new HashSet<>(Collections.singletonList(new Item(g, g.startingSymbol, g.getProd(g.startingSymbol).iterator().next().getValue(),1))), g));
        C.add(s0);
        int At=0,lastAt=-1;
        while(At!=lastAt){
            lastAt=At;
            int startsize=C.size();
            for(int i=lastAt;i<startsize;i++){
                Set<Item> s=C.get(i);
                for (String X:g.nonTerminals) {
                    Set<Item> res=goTo(s,X,g);
                    if(res!=null&&!C.contains(res)){
                        At++;
                        C.add(res);
                }
            }
                for (String X:g.terminals) {
                    Set<Item> res=goTo(s,X,g);
                    if(res!=null&&!C.contains(res)){
                        At++;
                        C.add(res);
                    }
                }
            }
        }
        return C;

    }

}

