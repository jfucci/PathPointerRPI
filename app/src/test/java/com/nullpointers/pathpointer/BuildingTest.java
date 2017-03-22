package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Building class
 *
 * @author Matthew R. Dobbins
 * Created on Wed, 2017-Mar-23
 */
public class BuildingTest {

    private static String buildName;
    private static Integer buildID;
    private static Set<Room> roomsA;
    private static Set<Room> roomsB;
    private static Set<Facility> facilitiesA;
    private static Set<Facility> facilitiesB;
    private static Building b;

    @BeforeClass
    public static void init() {
        // A location ID which will increment each time a new location is created
        int locID = 57;

        // Give the Building a name and ID to work with
        buildName = "Hogwarts";
        buildID = 4;

        // Initialize collections of Rooms to be used throughout this test
        String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};

        roomsA = new HashSet<>();
        for (int i = 0; i < names.length / 2; i++) {
            roomsA.add(new Room(locID, buildID, i + 3.14, i - 2.72, names[i], buildName));
            locID++;
        }

        roomsB = new HashSet<>();
        for (int i = names.length / 2; i < names.length; i++) {
            roomsB.add(new Room(locID, buildID, i + 3.14, i - 2.72, names[i], buildName));
            locID++;
        }

        // Initialize collections of Facilities to be used throughout this test
        FacilityType[] type = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer, FacilityType.VendingMachine};

        facilitiesA = new HashSet<>();
        for (int i = 0; i < 2 * type.length; i++) {
            facilitiesA.add(new Facility(locID, buildID, i * 1.4, i + 4.0, type[i % type.length]));
        }

        facilitiesB = new HashSet<>();
        for (int i = 2 * type.length; i < 4 * type.length; i++) {
            facilitiesB.add(new Facility(locID, buildID, i * 1.4, i + 4.0, type[i % type.length]));
        }

        // This building object will be used repeatedly throughout these test cases
        b = new Building(facilitiesA, roomsA, buildName, buildID);
    }

    @Test
    public void testDefaultConstructor() {
        Building b = new Building();

        // The default building should be null or empty
        assertNull(b.getID());
        assertNull(b.getName());
        assertEquals(0, b.countFacilities());
        assertEquals(0, b.countRooms());
    }

    @Test
    public void testGetters() {
        // The typical case: all arguments non-null
        assertEquals(buildName, b.getName());
        assertEquals(buildID, b.getID());

        // The building name is null
        Building b2 = new Building(facilitiesA, roomsA, null, buildID);
        assertNull(b2.getName());
        assertEquals(buildID, b2.getID());

        // The building ID is null
        Building b3 = new Building(facilitiesA, roomsA, buildName, null);
        assertEquals(buildName, b3.getName());
        assertNull(b3.getID());
    }

    @Test
    public void testContains() {
        // [b] should contain all the facilities and rooms in group A...
        for (Room r : roomsA) {
            assertTrue(b.contains(r));
        }

        for (Facility f : facilitiesA) {
            assertTrue(b.contains(f));
        }

        // ...and none in group B
        for (Room r : roomsB) {
            assertFalse(b.contains(r));
        }

        for (Facility f : facilitiesB) {
            assertFalse(b.contains(f));
        }
    }

    @Test
    public void testCountFacilities() {
        assertEquals(facilitiesA.size(), b.countFacilities());
    }

    @Test
    public void testCountFacilitiesOfType() {
        // [facilitiesA] should contain two facilities of each type
        assertEquals(2, b.countFacilitiesOfType(FacilityType.Bathroom));
        assertEquals(2, b.countFacilitiesOfType(FacilityType.WaterFountain));
        assertEquals(2, b.countFacilitiesOfType(FacilityType.Printer));
        assertEquals(2, b.countFacilitiesOfType(FacilityType.VendingMachine));
    }

    @Test
    public void testCountRooms() {
        assertEquals(roomsA.size(), b.countRooms());
    }

    @Test
    public void testFacilityIterator() {
        // [b] should contain all of the facilities in [facilitiesA]
        // Iterate through the facilities of [b], removing them from [facilitiesA] as we go
        // Use a clone of [facilitiesA] so that the original data is preserved
        Set<Facility> facilitiesA2 = new HashSet<>();
        facilitiesA2.addAll(facilitiesA);

        // Iterate through each type of facility
        FacilityType[] types = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer, FacilityType.VendingMachine};

        Iterator<Facility> facilityIterator;
        for (FacilityType type : types) {
            facilityIterator = b.facilityIterator(type);
            while (facilityIterator.hasNext()) {
                Facility f = facilityIterator.next();
                assertTrue(facilitiesA2.contains(f));

                // The facilities being iterated through should all match the requested type
                assertEquals(type, f.getType());
                facilitiesA2.remove(f);
            }
        }

        // All elements should have been iterated through, which means the copy set from which
        // Facilities were removed should be empty
        assertTrue(facilitiesA2.isEmpty());
    }

    @Test
    public void testRoomIterator() {
        // [b] should contain all of the rooms in [roomsA]
        // Iterate through the rooms of [b], removing them from [roomsA] as we go
        // Use a clone of [roomsA] so that the original data is preserved
        Set<Room> roomsA2 = new HashSet<>();
        roomsA2.addAll(roomsA);

        // Iterate through all rooms
        Iterator<Room> roomIterator = b.roomIterator();
        while (roomIterator.hasNext()) {
            Room r = roomIterator.next();
            assertTrue(roomsA2.contains(r));
            roomsA2.remove(r);
        }

        // All elements should have been iterated through, which means the copy set from which
        // Facilities were removed should be empty
        assertTrue(roomsA2.isEmpty());
    }
}
