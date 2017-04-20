package com.nullpointers.pathpointer;

/**
 * Represents all valid types of facilities.
 */
public enum FacilityType {
    Bathroom(0), MBathroom(1), WBathroom(2), WaterFountain(3), Printer(4), VendingMachine(5);

    private final int value;

    private FacilityType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}