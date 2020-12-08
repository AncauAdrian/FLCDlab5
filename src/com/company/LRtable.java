package com.company;

import java.util.*;

public class LRtable {
    static final String actionstr="action";
    static final Integer shift=-1;
    static final Integer acc=-2;
    static final Integer err=-3;
    private List<Map<String,Integer>> table;
    private List<Set<Item>> states;
    private Grammar g;
    public LRtable(List<Set<Item>> states,Grammar g){
        this.states=states;
        this.g=g;
        table=new ArrayList<>();
        setup();
    }
    private void setup(){
        for(int i=0;i<states.size();i++){
            table.add(i,new HashMap<>());
            Map<String,Integer> row=table.get(i);
            //action
            Integer action=null;
            for (Item itm:states.get(i)) {
                if(itm.afterDot()!=null) // does there need to be something before dot for rule 1?
                {
                    if(action==null)
                        action=shift;
                    else if(action>0)
                        throw new Error("shift-reduce conflict at state:"+states.get(i));
                }
                else{
                    if(itm.start.equals(g.startingSymbol))
                        action=acc;
                    else if(action==null)
                        action=itm.prodNR;
                    else if (action.equals(shift)) {
                        throw new Error("shift-reduce conflict at state:" + states.get(i));
                    } else {
                        throw new Error("reduce-reduce conflict at state:" + states.get(i));
                    }
                }
            }
            if(action==null)
                action=err;
            row.put(actionstr,action);
            //goto

            for (String X:g.nonTerminals) {
                int index=states.indexOf(Parser.goTo(states.get(i),X,g));
                if(index>=0)
                    row.put(X,index);
                else
                    row.put(X,err);
            }
            for (String X:g.terminals) {
                int index=states.indexOf(Parser.goTo(states.get(i),X,g));
                if(index>=0)
                    row.put(X,index);
                else
                    row.put(X,err);
            }
        }
    }
    public void printTable(){
        Map map=Map.of(acc,"acc",shift,"shift",err,"err");
        String actionrow="actions:";
        for(int i=0;i<states.size();i++){
            System.out.println("s"+i+":"+states.get(i));
            if(map.containsKey(table.get(i).get(actionstr)))
                actionrow+=map.get(table.get(i).get(actionstr))+" | ";
            else
                actionrow+=table.get(i).get(actionstr)+" | ";
        }
        System.out.println(actionrow);
        for (String X:g.nonTerminals) {
            String row=X+":";
            for (Map<String,Integer> state:table) {
                if(map.containsKey(state.get(X)))
                    row+=map.get(state.get(X))+" | ";
                else
                    row+=state.get(X)+" | ";
            };
            System.out.println(row);
        }
        for (String X:g.terminals) {
            String row=X+":";
            for (Map<String,Integer> state:table) {
                if(map.containsKey(state.get(X)))
                    row+=map.get(state.get(X))+" | ";
                else
                    row+=state.get(X)+" | ";
            };
            System.out.println(row);
        }
    }
}

