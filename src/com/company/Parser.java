//package com.company;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
////---WARNING---
////EXTREMELY SENSELESS CODE AHEAD
////basically just structure guidance to make completing it faster once i understand the algorithms
//public class Parser {
//    private Object closure(List<Object> items,Grammar g) {
//        C=items;
//        boolean changed=true;
//        while(changed){
//            changed=false;
//            for(somekindofitem in C)
//                for({B->y} in P)
//                    if(!C.contains(B-item)){
//                        changed=true;
//                        C.add(B-item);
//                    }
//        }
//        return c;
//    }
//
//    public void goTo(Object state,Object X) {
//        return closure(something);
//    }
//
//    public void canonicalCollection(Grammar g) {
//        Set<Object> C=new HashSet<Object>();
//        List<Object> s0=closure(g.productions.get(g.startingSymbol));
//        s0.forEach(prod->C.add(prod));
//        boolean changed=true;
//        while(changed){
//            changed=false;
//            for(s in C)
//                for(X in N and E){
//                    res=goTo(s,X);
//                    if(res.size>0&&!C.contains(res)){
//                        changed=true;
//                        C.add(res);
//                    }
//            }
//        }
//
//    }
//
//}
