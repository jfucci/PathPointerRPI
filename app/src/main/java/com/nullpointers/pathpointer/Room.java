package com.nullpointers.pathpointer;

/**
 * Created by Anthony on 3/20/2017.
 */
public class Room extends Location {
  private String name;
  private String building;

  /** @returns a Room with the provided attributes */
  public Room(Integer id, Integer floorPlan, Double x, Double y, String name, String building) {
    super(id, floorPlan, x, y);
    this.name = name;
    this.building = building;
  }

  /** @returns a Room with null attributes */
  public Room() {
    super();
    this.name = null;
    this.building = null;
  }

  /** @returns the name of this room */
  public String getName() {return name;}

  /** @returns the building this room is in */
  public String getBuilding() {return building;}
}
