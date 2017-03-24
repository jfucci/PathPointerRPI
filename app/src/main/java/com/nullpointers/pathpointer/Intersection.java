package com.nullpointers.pathpointer;

/**
 * Created by Anthony on 3/20/2017.
 */
public class Intersection extends Location {
    /** Returns an intersection with null attributes */
    public Intersection() {
        super();
    }

    /** Returns an intersection with given attributes */
    public Intersection(Integer id, Integer floorPlan, Double x, Double y) {
        super(id,floorPlan,x,y);
    }
}


