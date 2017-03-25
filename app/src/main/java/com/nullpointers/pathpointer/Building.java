package com.nullpointers.pathpointer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
        id = null;
        name = null;
        facilities = new HashMap<>();
        rooms = new HashSet<>();
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
        this.id = id;
        this.name = name;
        this.rooms = new HashSet<>(rooms);

        // Organize facilities by type
        this.facilities = new HashMap<>();
        Iterator<Facility> iter = facilities.iterator();
        while (iter.hasNext()) {
            Facility fac = iter.next();
            if (this.facilities.containsKey(fac.getType())) {
                this.facilities.get(fac.getType()).add(fac);
            } else {
                Set<Facility> newSet = new HashSet<>();
                newSet.add(fac);
                this.facilities.put(fac.getType(), newSet);
            }
        }
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Checks whether or not this Building contains the specified Facility.
     * @param facility The Facility which may or may not be located in this Building
     * @return true if this Building contains the specified Facility, false otherwise
     */
    public boolean contains(Facility facility) {
        if (!facilities.containsKey(facility.getType())) {
            return false;
        } else {
            return facilities.get(facility.getType()).contains(facility);
        }
    }

    /**
     * Checks whether or not this Building contains the specified Room.
     * @param room The Room which may or may not be located in this Building
     * @return true if this Building contains the specified Room, false otherwise
     */
    public boolean contains(Room room) {
        return rooms.contains(room);
    }

    /** Returns the number of Facilities of all types contained in this Building */
    public int countFacilities() {
        // Count the facilities of each type
        int total = 0;
        Iterator<Set<Facility>> iter = facilities.values().iterator();
        while (iter.hasNext()) {
            total += iter.next().size();
        }
        return total;
    }

    /**
     * @param type The type of Facility to be counted
     * @return The number of Facilities of specified type contained in this Building
     */
    public int countFacilitiesOfType(FacilityType type) {
        if (facilities.containsKey(type)) {
            // If there are some facilities of this type, the type will appear in the map
            return facilities.get(type).size();
        } else {
            // If there are no facilities of this type, the type will not appear in the map
            return 0;
        }
    }

    /** Returns the number of Rooms contained in this Building */
    public int countRooms() {
        return rooms.size();
    }

    /**
     * @param type The type of Facility to iterate over
     * @return An Iterator which can be used to traverse all Facilities of specified type in this
     * Building
     */
    public Iterator<Facility> facilityIterator(FacilityType type) {
        if (facilities.containsKey(type)) {
            // If there are some facilities of this type, the type will appear in the map
            return Collections.unmodifiableMap(facilities).get(type).iterator();
        } else {
            // If there are no facilities of this type, the type will not appear in the map
            // Return an iterator to an empty set
            return (new HashSet<Facility>()).iterator();
        }
    }

    /** Returns an Iterator which can be used to traverse all Rooms in this Building */
    public Iterator<Room> roomIterator() {
        return Collections.unmodifiableSet(rooms).iterator();
    }

}
