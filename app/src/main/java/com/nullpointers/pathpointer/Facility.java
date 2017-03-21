package com.nullpointers.pathpointer;

/**
 * Created by Anthony on 3/20/2017.
 */
public class Facility extends Location{
  private FacilityType type;

  /** @returns a Facility with the provided attributes */
  public Facility(Integer id, Integer floorPlan, Double x, Double y, FacilityType type) {
    super(id, floorPlan, x, y);
    this.type = type;
  }

  /** @returns a Facility with null attributes */
  public Facility() {
    super();
    this.type = null;
  }

  /** @returns the type of this facility */
  public FacilityType getType() {return type;}
}
