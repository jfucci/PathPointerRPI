package com.nullpointers.pathpointer;

/**
 * Created by Anthony on 3/20/2017.
 */
public abstract class Location {
    protected Integer id;
    protected Double x;
    protected Double y;

    //id number of the floor plan image for this location
    protected Integer floorPlan;

    /** @returns creates a Location with the provided attributes */
    public Location(Integer id, Integer floorPlan, Double x, Double y) {
        this.id = id;
        this.floorPlan = floorPlan;
        this.x = x;
        this.y = y;
    }

    /** @returns creates a Location with null attributes */
    public Location() {
        this.id = null;
        this.floorPlan = null;
        this.x = null;
        this.y = null;
    }

    /** @returns the id of this location*/
    public Integer getId() {return id;}

    /** @returns the id of the floor plan associated with this location */
    public Integer getFloorPlan() {return floorPlan;}

    /** @returns the x coordinate of this location */
    public Double getX() {return x;}

    /** @returns the y coordinate of this location */
    public Double getY() {return y;}

}
