package com.nullpointers.pathpointer;

/**
 * An abstract base class to represent locations within buildings.
 * All locations must have a unique id.
 *
 * Note that the default constructor is provided only for compatibility.
 * All usage of default constructed objects is undefined.
 */
public abstract class Location {
    protected Integer id;
    protected Double x;
    protected Double y;

    //id number of the floor plan image for this location
    protected Integer floorPlan;

    /** Returns a Location with the provided attributes */
    public Location(Integer id, Integer floorPlan, Double x, Double y) {
        this.id = id;
        this.floorPlan = floorPlan;
        this.x = x;
        this.y = y;
    }

    /** Returns a Location with null attributes */
    public Location() {
        this.id = null;
        this.floorPlan = null;
        this.x = null;
        this.y = null;
    }

    /** Returns the id of this location*/
    public Integer getId() {return id;}

    /** Returns the id of the floor plan associated with this location */
    public Integer getFloorPlan() {return floorPlan;}

    /** Returns the x coordinate of this location */
    public Double getX() {return x;}

    /** Returns the y coordinate of this location */
    public Double getY() {return y;}

    @Override
    public boolean equals( Object other) {
        if (!(other instanceof Location)) {
            return false;
        } else {
            Location o = (Location) other;
            return this.getId().equals(o.getId());
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
