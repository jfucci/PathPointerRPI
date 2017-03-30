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
    private Set<Room> rooms;
    private Map<FacilityType, Set<Facility>> facilities;

    /** Constructs a null Building. */
    public Building() {
        id = null;
        name = null;
        rooms = new HashSet<>();
        facilities = new HashMap<>();
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
        if(facilities != null) {
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
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Ensures that this Building contains the specified Room
     * @param r The Room to add to the Building.
     * @return True if the collection of Rooms in this Building changed due to this call
     */
    public boolean add(Room r) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Ensures that this Building contains the specified Facility
     * @param f The Facility to add to the Building.
     * @return True if the collection of Facilities in this Building changed due to this call
     */
    public boolean add(Facility f) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Ensures that this Building contains each Room in the specified Collection
     * @param r The Collection of Rooms to add to the Building.
     * @return True if the collection of Rooms in this Building changed due to this call
     */
    public boolean addAllRooms(Collection<? extends Room> r) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Ensures that this Building contains each Facility in the specified Collection
     * @param f The Collection of Facilities to add to the Building.
     * @return True if the collection of Facilities in this Building changed due to this call
     */
    public boolean addAllFacilities(Collection<? extends Facility> f) {
        throw new RuntimeException("Method not implemented");
    }

    /** Removes all Rooms and Facilities from this Building */
    public void clear() {
        throw new RuntimeException("Method not implemented");
    }

    /** Removes all Rooms from this Building; Facilities are unchanged */
    public void clearRooms() {
        throw new RuntimeException("Method not implemented");
    }

    /** Removes all Facilities from this Building; Rooms are unchanged */
    public void clearFacilities() {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Checks whether or not this Building contains the specified Room.
     * @param room The Room which may or may not be located in this Building
     * @return true if this Building contains the specified Room, false otherwise
     */
    public boolean contains(Room room) {
        return rooms.contains(room);
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

    /** Returns the number of Rooms contained in this Building */
    public int countRooms() {
        return rooms.size();
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

    /** Returns an immutable Iterator which can be used to traverse all Rooms in this Building */
    public Iterator<Room> roomIterator() {
        return Collections.unmodifiableSet(rooms).iterator();
    }

    /**
     * @param type The type of Facility to iterate over
     * @return An immutable Iterator which can be used to traverse all Facilities of specified type
     * in this Building
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

    /**
     * Removes a single instance of the specified Room from this Building, if it is present.
     * @param r The Room to remove
     * @return True if the collection of Rooms in this Building changed due to this call
     */
    boolean remove(Room r) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Removes a single instance of the specified Facility from this Building, if it is present.
     * @param f The Facility to remove
     * @return True if the collection of Facilities in this Building changed due to this call
     */
    boolean remove(Facility f) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Removes all instances of each of the specified Rooms from this Building.  Any room [o] which
     * satisfies r.contains(o) == true will be removed.
     * @param r The Collection of Rooms to remove
     * @return True if the collection of Rooms in this Building changed due to this call
     */
    boolean removeAllRooms(Collection<? extends Room> r) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Removes all instances of each of the specified Facilities from this Building.  Any facility
     * [o] which satisfies f.contains(o) == true will be removed.
     * @param f The Collection of Facilities to remove
     * @return True if the collection of Facilities in this Building changed due to this call
     */
    boolean removeAllFacilities(Collection<? extends Facility> f) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Retains only instances of each of the specified Rooms from this Building.  Any room which
     * satisfies r.contains(o) == false will be removed.
     * @param r The Collection of Rooms to retain
     * @return True if the collection of Rooms in this Building changed due to this call
     */
    boolean retainAllRooms(Collection<? extends Room> r) {
        throw new RuntimeException("Method not implemented");
    }

    /**
     * Retains only instances of each of the specified Facilities from this Building.  Any facility
     * [o] which satisfies f.contains(o) == false will be removed.
     * @param f The Collection of Facilities to retain
     * @return True if the collection of Facilities in this Building changed due to this call
     */
    boolean retainAllFacilities(Collection<? extends Facility> f) {
        throw new RuntimeException("Method not implemented");
    }

}
