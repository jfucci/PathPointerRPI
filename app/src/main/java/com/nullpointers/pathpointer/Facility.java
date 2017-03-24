package com.nullpointers.pathpointer;

/**
 * A class for facilities.
 */
public class Facility extends Location{
  private FacilityType type;

  /** Returns a Facility with the provided attributes */
  public Facility(Integer id, Integer floorPlan, Double x, Double y, FacilityType type) {
    super(id, floorPlan, x, y);
    this.type = type;
  }

  /** Returns a Facility with null attributes */
  public Facility() {
    super();
    this.type = null;
  }

  /** Returns the type of this facility */
  public FacilityType getType() {return type;}
}
