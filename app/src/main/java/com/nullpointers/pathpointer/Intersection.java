package com.nullpointers.pathpointer;

/**
 * Represents an intersection of paths on the campus.  This is not a destination itself.
 * Rather, it is an intermediate point that is included in the graph as an intermediary between
 * other types of Locations, such as: rooms and facilities.
 */
public class Intersection extends Location {
    /** Returns an intersection with null attributes */
    public Intersection() {super();}

    /** Returns an intersection with given attributes */
    public Intersection(Integer id, Integer floorPlan, Double x, Double y) {
        super(id,floorPlan,x,y);
    }
}


