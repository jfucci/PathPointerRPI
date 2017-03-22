package com.nullpointers.pathpointer;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The Building class represents a Building on the Campus.  It contains references to all the Rooms
 * and Facilities in a Building, and provides functions to view but not edit these collections.
 *
 * @author Matthew R. Dobbins
 * Created on Wed, 2017-Mar-23
 */
public class Building {

    private Integer id;
    private String name;
    private Map<FacilityType, Set<Facility>> facilities;
    private Set<Room> rooms;

    /** Constructs a null Building. */
    public Building() {

    }

    /**
     * Constructs a new Building, containing the specified Facilities and Rooms.
     * @param facilities The Facilities located in this Building.  If this argument is null, this
     *     Building will contain zero facilities
     * @param rooms The Rooms located in this Building.  If this argument is null, this Building
     *      will contain zero rooms
     * @param name This Building's Name
     * @param id This Building's ID
     */
    public Building(Set<Facility> facilities, Set<Room> rooms, String name, Integer id) {

    }

    public Integer getID() {
        return null;
    }

    public String getName() {
        return null;
    }

    /**
     * Checks whether or not this Building contains the specified Facility.
     * @param facility The Facility which may or may not be located in this Building
     * @return true if this Building contains the specified Facility, false otherwise
     */
    public boolean contains(Facility facility) {
        return false;
    }

    /**
     * Checks whether or not this Building contains the specified Room.
     * @param room The Room which may or may not be located in this Building
     * @return true if this Building contains the specified Room, false otherwise
     */
    public boolean contains(Room room) {
        return false;
    }

    /** Returns the number of Facilities of all types contained in this Building */
    public int countFacilities() {
        return 0;
    }

    /**
     * @param type The type of Facility to be counted
     * @return The number of Facilities of specified type contained in this Building
     */
    public int countFacilitiesOfType(FacilityType type) {
        return 0;
    }

    /** Returns the number of Rooms contained in this Building */
    public int countRooms() {
        return 0;
    }

    /**
     * @param type The type of Facility to iterate over
     * @return An Iterator which can be used to traverse all Facilities of specified type in this
     * Building
     */
    public Iterator<Facility> facilityIterator(FacilityType type) {
        return null;
    }

    /** Returns an Iterator which can be used to traverse all Rooms in this Building */
    public Iterator<Room> roomIterator() {
        return null;
    }

}
