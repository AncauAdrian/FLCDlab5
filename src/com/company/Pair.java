package com.company;

public class Pair<K, V> {
    public K first;
    public V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair)
        {
            return ((Pair<?, ?>) obj).first.equals(this.first) && ((Pair<?, ?>) obj).second.equals(this.second);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        if(first != null && second != null)
            return "[" + first.toString() + ", " + second.toString() + "]";

        if(first == null && second == null)
            return "[null, null]";

        if(first != null)
            return "[" + first.toString() + ", null]";

        return "[null, " + second.toString() + "]";
    }
}
