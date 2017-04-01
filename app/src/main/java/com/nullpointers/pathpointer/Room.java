package com.nullpointers.pathpointer;

/**
 * Represents a classroom on campus.
 */
public class Room extends Location {
  private String name;
  private String building;

  /** Returns a Room with the provided attributes */
  public Room(Integer id, Integer floorPlan, Double x, Double y, String name, String building) {
    super(id, floorPlan, x, y);
    this.name = name;
    this.building = building;
  }

  /** Returns a Room with null attributes */
  public Room() {
    super();
    this.name = null;
    this.building = null;
  }

  /** Returns the name of this room */
  public String getName() {return name;}

  /** Returns the building this room is in */
  public String getBuilding() {return building;}
}
