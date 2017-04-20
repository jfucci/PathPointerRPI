package com.nullpointers.pathpointer;

public class StringWithTag implements Comparable<StringWithTag> {
    public String string;
    public Object tag;

    public StringWithTag(String string, Object tag) {
        this.string = string;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public int compareTo(StringWithTag other) {
        return string.compareTo(other.toString());
    }
}