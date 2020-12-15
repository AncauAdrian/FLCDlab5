package com.company;

import java.util.*;

public class Parser {
    private static Set<Item> closure(Set<Item> items,Grammar g) {
        Set<Item> C = new HashSet<>(items);
        Set<Item> visited=new HashSet<>();
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<Item> C1 = new HashSet<>(C);
            C1.removeAll(visited); //foreach on C breaks for g2 |ConcurrentModificationException|
            for (Item itm : C1) {
                if(!visited.contains(itm)){
                    visited.add(itm);
                    int pos = itm.dotPos + 1;
                    if (pos < itm.prod.size()) {
                        String symbol = itm.prod.get(itm.dotPos + 1);
                        if(g.productions.get(symbol)!=null){
                            for (Pair<Integer,String> production : g.productions.get(symbol)) {
                                Item newItm = new Item(g, symbol, production.second,production.first);
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
        Set<Item> s0 = new HashSet<>(closure(new HashSet<>(Collections.singletonList(new Item(g, g.startingSymbol, g.getProd(g.startingSymbol).iterator().next().second,1))), g));
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
            At=startsize;
        }
        return C;

    }
    public static List<Integer> fullparse(Grammar g,List<String> w){
        return parse(g,w,new LRtable(canonicalCollection(g),g));
    }
    public static List<Integer> parse(Grammar g,List<String> beta,LRtable table){
        Stack<Object> alpha=new Stack<>();
        List<Integer> pi=new ArrayList<>();
        alpha.push(0);
        Integer state=0;
        while (true){
            if(state.equals(LRtable.err)){
                throw new Error("Parsing error [Last Config alpha:"+alpha+"beta:"+beta+"pi"+pi+"]");
            }
            if(table.action(state).equals(LRtable.acc))
                return pi;
            System.out.println("Current config:"+alpha+beta+pi);
            if(table.action(state).equals(LRtable.shift)){
                if(beta.isEmpty())
                    throw new Error("Parsing error [Last Config alpha:"+alpha+"beta:"+beta+"pi"+pi+"]");
                String a=beta.remove(0);
                state=table.goTo(state,a);
                alpha.push(a);
                alpha.push(state);
            }
            else{
                if(LRtable.reduce(table.action(state))){
                    Pair<String,String> res=g.productionByNR(table.action(state)); //<lhp,rhp>
                    String pop="";
                    while(!pop.equals(res.second)){
                        alpha.pop();
                        pop=alpha.pop()+pop;
                    }
                    pi.add(0,table.action(state));
                    state=table.goTo((Integer)alpha.peek(),res.first);
                    alpha.push(res.first);
                    alpha.push(state);
                }
            }
        }
    }
}

//alpha working stack(Pair(symbol,state)) beta(w) output stack pi output