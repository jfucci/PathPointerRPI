package com.nullpointers.pathpointer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A class to represent facilities on RPI's Campus.
 * Valid Facility Types are defined by the FacilityType Enum.
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

    protected Facility(Parcel in) {
        super(in);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Facility(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Facility[size];
        }
    };
}
