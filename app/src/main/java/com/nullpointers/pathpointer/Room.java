package com.nullpointers.pathpointer;

import android.os.Parcel;
import android.os.Parcelable;

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

  protected Room(Parcel in) {
    super(in);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
    @Override
    public Location createFromParcel(Parcel in) {
      return new Room(in);
    }

    @Override
    public Location[] newArray(int size) {
      return new Room[size];
    }
  };
}
