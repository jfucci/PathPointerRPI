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
    private static Set<Facility> facA;
    private static Set<Facility> facB;
    private static Building bA;
    private static Building bB;

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
        FacilityType[] typeA = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer, FacilityType.VendingMachine};

        facA = new HashSet<>();
        for (int i = 0; i < 2 * typeA.length; i++) {
            facA.add(new Facility(locID, buildID, i * 1.4, i + 4.0, typeA[i % typeA.length]));
        }

        FacilityType[] typeB = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer};

        facB = new HashSet<>();
        for (int i = 0; i < 2 * typeB.length; i++) {
            facB.add(new Facility(locID, buildID, i * 1.4, i + 4.0, typeB[i % typeB.length]));
        }

        // These building object will be used repeatedly throughout these test cases
        bA = new Building(facA, roomsA, buildName, buildID);
        bB = new Building(facB, roomsB, buildName, buildID);
    }

    ///////////////////////////
    // BEGIN BLACK BOX TESTS //
    ///////////////////////////

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
        assertEquals(buildName, bA.getName());
        assertEquals(buildID, bA.getID());

        // The building name is null
        Building b2 = new Building(facA, roomsA, null, buildID);
        assertNull(b2.getName());
        assertEquals(buildID, b2.getID());

        // The building ID is null
        Building b3 = new Building(facA, roomsA, buildName, null);
        assertEquals(buildName, b3.getName());
        assertNull(b3.getID());
    }

    @Test
    public void testContains() {
        // [bA] should contain all the facilities and rooms in group A...
        for (Room r : roomsA) {
            assertTrue(bA.contains(r));
        }

        for (Facility f : facA) {
            assertTrue(bA.contains(f));
        }

        // ...and none in group B
        for (Room r : roomsB) {
            assertFalse(bA.contains(r));
        }

        for (Facility f : facB) {
            assertFalse(bA.contains(f));
        }

        // [bB] should contain all the facilities and rooms in group B...
        for (Room r : roomsB) {
            assertTrue(bB.contains(r));
        }

        for (Facility f : facB) {
            assertTrue(bB.contains(f));
        }

        // ...and none in group A
        for (Room r : roomsA) {
            assertFalse(bB.contains(r));
        }

        for (Facility f : facA) {
            assertFalse(bB.contains(f));
        }
    }

    @Test
    public void testCountFacilities() {
        assertEquals(facA.size(), bA.countFacilities());
    }

    @Test
    public void testCountFacilitiesOfType() {
        // [facA] should contain two facilities of each type
        assertEquals(2, bA.countFacilitiesOfType(FacilityType.Bathroom));
        assertEquals(2, bA.countFacilitiesOfType(FacilityType.WaterFountain));
        assertEquals(2, bA.countFacilitiesOfType(FacilityType.Printer));
        assertEquals(2, bA.countFacilitiesOfType(FacilityType.VendingMachine));
    }

    @Test
    public void testCountRooms() {
        assertEquals(roomsA.size(), bA.countRooms());
    }

    @Test
    public void testFacilityIterator() {
        // [bA] should contain all of the facilities in [facA]
        // Iterate through the facilities of [bA], removing them from [facA] as we go
        // Use a clone of [facA] so that the original data is preserved
        Set<Facility> facA2 = new HashSet<>();
        facA2.addAll(facA);

        // Iterate through each type of facility
        FacilityType[] types = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer, FacilityType.VendingMachine};

        Iterator<Facility> facilityIterator;
        for (FacilityType type : types) {
            facilityIterator = bA.facilityIterator(type);
            while (facilityIterator.hasNext()) {
                Facility f = facilityIterator.next();
                assertTrue(facA2.contains(f));

                // The facilities being iterated through should all match the requested type
                assertEquals(type, f.getType());
                facA2.remove(f);
            }
        }

        // All elements should have been iterated through, which means the copy set from which
        // Facilities were removed should be empty
        assertTrue(facA2.isEmpty());
    }

    @Test
    public void testRoomIterator() {
        // [bA] should contain all of the rooms in [roomsA]
        // Iterate through the rooms of [bA], removing them from [roomsA] as we go
        // Use a clone of [roomsA] so that the original data is preserved
        Set<Room> roomsA2 = new HashSet<>();
        roomsA2.addAll(roomsA);

        // Iterate through all rooms
        Iterator<Room> roomIterator = bA.roomIterator();
        while (roomIterator.hasNext()) {
            Room r = roomIterator.next();
            assertTrue(roomsA2.contains(r));
            roomsA2.remove(r);
        }

        // All elements should have been iterated through, which means the copy set from which
        // Facilities were removed should be empty
        assertTrue(roomsA2.isEmpty());
    }

    ///////////////////////////
    // BEGIN WHITE BOX TESTS //
    ///////////////////////////

    @Test
    public void testCountFacilitiesOfType_expectZero() {
        // A different branch is taken if there are no facilities of a given type in a Building
        // [bB] contains no vending machines; use this building to test this branch
        assertEquals(0, bB.countFacilitiesOfType(FacilityType.VendingMachine));
    }

    @Test
    public void testFacilitiyIterator_empty() {
        // A different branch is taken if there are no facilities of a given type in a Building
        // [bB] contains no vending machines; use this building to test this branch
        Iterator<Facility> iter = bB.facilityIterator(FacilityType.VendingMachine);
        assertFalse(iter.hasNext());
    }
}
