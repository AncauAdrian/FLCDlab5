package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    private Grammar g;
    public String start;
    public List<String> prod;
    public Integer dotPos;
    private static final String dot=".";
    public Item(Grammar gr,String startsymbol,List<String> prodOUT,Integer dot){
        g=gr;
        start=startsymbol;
        dotPos=dot;
        prod=prodOUT;
    }
    public Item(Grammar gr,String startsymbol,String productionOUT){
        g=gr;
        if(!g.nonTerminals.contains(startsymbol) && !g.terminals.contains(startsymbol))
            throw new Error("Symbol does not match grammar: "+startsymbol);
        start=startsymbol;
        dotPos=0;
        prod=new ArrayList<String>();
        prod.add(dot);
        productionOUT=productionOUT.strip();
        while(productionOUT.length()>0){
            String symb=nextSymbol(productionOUT);
            prod.add(symb);
            productionOUT=productionOUT.substring(symb.length());
        }
    }
    public Item incrementDot(){
        if(dotPos==prod.size()-1)
            return null;
        List<String> copyprod=new ArrayList<String>();
        copyprod.addAll(prod);
        copyprod.remove(dot);
        copyprod.add(dotPos+1,dot);
        return new Item(g,start,copyprod,dotPos+1);
    }
    public Item moveDotAfter(String symbol){
        int pos=prod.indexOf(symbol);
        if(pos==-1)
            return null;
        List<String> copyprod=new ArrayList<String>();
        copyprod.addAll(prod);
        copyprod.remove(dot);
        copyprod.add(pos,dot);
        return new Item(g,start,copyprod,pos);
    }
    public String afterDot(){
        if(dotPos==prod.size()-1)
            return null;
        return prod.get(dotPos+1);
    }
    private String nextSymbol(String prod) {
        String res = "";
        int index = 0;
        while (index < prod.length()) {
            res += prod.charAt(index);
            if (g.nonTerminals.contains(res) || g.terminals.contains(res))
                return res;
            index++;
        }
        throw new Error("Symbol does not match grammar: "+prod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return start.equals(item.start) && prod.equals(item.prod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, prod, dotPos);
    }

    @Override
    public String toString() {
        return "Item{" +
                "g=" + g +
                ", start='" + start + '\'' +
                ", prod=" + prod +
                ", dotPos=" + dotPos +
                '}';
    }
}
