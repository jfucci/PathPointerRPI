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
            locID++;
        }

        FacilityType[] typeB = {FacilityType.Bathroom, FacilityType.WaterFountain,
                FacilityType.Printer};

        facB = new HashSet<>();
        for (int i = 0; i < 2 * typeB.length; i++) {
            facB.add(new Facility(locID, buildID, i * 1.4, i + 4.0, typeB[i % typeB.length]));
            locID++;
        }

        // These building object will be used repeatedly throughout these test cases
        bA = new Building(facA, roomsA, buildName, buildID);
        bB = new Building(facB, roomsB, buildName, buildID);
    }

    /////////////////////////////////////
    // BEGIN BLACK BOX IMMUTABLE TESTS //
    /////////////////////////////////////

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
    public void testGetName() {
        // The typical case: all arguments non-null
        assertEquals(buildName, bA.getName());

        // The building name is null
        Building bNullName = new Building(facA, roomsA, null, buildID);
        assertNull(bNullName.getName());
    }

    @Test
    public void testGetID() {
        // The typical case: all arguments non-null
        assertEquals(buildID, bA.getID());

        // The building ID is null
        Building b3 = new Building(facA, roomsA, buildName, null);
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

    /////////////////////////////////////
    // BEGIN WHITE BOX IMMUTABLE TESTS //
    /////////////////////////////////////

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

    @Test
    public void testFacilityImmutability() {
        // Iterators should not cause representation exposure and violate immutability
        Iterator<Facility> iter = bA.facilityIterator(FacilityType.Bathroom);
        boolean flag = false;
        try {
            iter.remove();
        } catch (Exception e) {
            flag = true;
        }

        assertTrue(flag);
        assertEquals(facA.size(), bA.countFacilities());
    }

    @Test
    public void testRoomImmutability() {
        // Iterators should not cause representation exposure
        Iterator<Room> iter = bA.roomIterator();
        boolean flag = false;
        try {
            iter.remove();
        } catch (Exception e) {
            flag = true;
        }

        assertTrue(flag);
        assertEquals(roomsA.size(), bA.countRooms());
    }

    ///////////////////////////////////
    // BEGIN BLACK BOX MUTABLE TESTS //
    ///////////////////////////////////

    @Test
    public void testAddRoom() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to re-add Rooms from set A, nothing should change
        int size = b.countRooms();
        for (Room r : roomsA) {
            assertFalse(b.add(r));
            assertEquals(size, b.countRooms());
        }

        // Add each Room in set B, asserting that it modifies the collection
        for (Room r : roomsB) {
            assertTrue(b.add(r));
            assertTrue(b.contains(r));
            assertEquals(++size, b.countRooms());
        }

        // Re-add each Room in set B, asserting that it no longer modifies the collection
        for (Room r : roomsB) {
            assertFalse(b.add(r));
            assertTrue(b.contains(r));
            assertEquals(size, b.countRooms());
        }
    }

    @Test
    public void testAddFacility() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to re-add Facilities from set A, nothing should change
        int size = b.countFacilities();
        for (Facility f : facA) {
            assertFalse(b.add(f));
            assertEquals(size, b.countFacilities());
        }

        // Add each Facility in set B, asserting that it modifies the collection
        for (Facility f : facB) {
            assertTrue(b.add(f));
            assertTrue(b.contains(f));
            assertEquals(++size, b.countFacilities());
        }

        // Re-add each Facility in set B, asserting that it no longer modifies the collection
        for (Facility f : facB) {
            assertFalse(b.add(f));
            assertTrue(b.contains(f));
            assertEquals(size, b.countFacilities());
        }
    }

    @Test
    public void testAddAllRooms() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to re-add Rooms from set A, nothing should change
        int size = b.countRooms();
        assertFalse(b.addAllRooms(roomsA));
        assertEquals(size, b.countRooms());

        // Add Rooms in set B, asserting that they modify the collection
        size += roomsB.size();
        assertTrue(b.addAllRooms(roomsB));
        assertEquals(size, b.countRooms());
        for (Room r : roomsB) {
            assertTrue(b.contains(r));
        }

        // Re-add Rooms in set B, asserting that they no longer modify the collection
        assertFalse(b.addAllRooms(roomsB));
        assertEquals(size, b.countRooms());
    }

    @Test
    public void testAddAllFacilities() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to re-add Facilities from set A, nothing should change
        int size = b.countFacilities();
        assertFalse(b.addAllFacilities(facA));
        assertEquals(size, b.countFacilities());

        // Add Facilities in set B, asserting that they modify the collection
        size += facB.size();
        assertTrue(b.addAllFacilities(facB));
        assertEquals(size, b.countFacilities());
        for (Facility f : facB) {
            assertTrue(b.contains(f));
        }

        // Re-add Facilities in set B, asserting that they no longer modify the collection
        assertFalse(b.addAllFacilities(facB));
        assertEquals(size, b.countFacilities());
    }

    @Test
    public void testClear() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // After a Building is cleared, it should contain no Rooms or Facilities
        b.clear();
        assertEquals(0, b.countRooms());
        assertEquals(0, b.countFacilities());
    }

    @Test
    public void testClearRooms() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // After a Building is cleared of Rooms, it should contain no Rooms but Facilities should
        // be unchanged
        b.clearRooms();
        assertEquals(0, b.countRooms());
        assertEquals(facA.size(), b.countFacilities());
    }

    @Test
    public void testClearFacilities() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // After a Building is cleared of Facilities, it should contain no Facilities but Rooms
        // should be unchanged
        b.clearFacilities();
        assertEquals(roomsA.size(), b.countRooms());
        assertEquals(0, b.countFacilities());
    }

    @Test
    public void testRemoveRoom() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to remove each room in set B, an action which should not modify the collection
        int size = b.countRooms();
        for (Room r : roomsB) {
            assertFalse(b.remove(r));
            assertEquals(size, b.countRooms());
        }

        // Remove each room from set A, asserting that the size decrements each time
        for (Room r : roomsA) {
            assertTrue(b.remove(r));
            assertFalse(b.contains(r));
            assertEquals(--size, b.countRooms());
        }
        assertEquals(0, b.countRooms());

        // Re-remove each Room in set A, asserting that it no longer modifies the collection
        for (Room r : roomsA) {
            assertFalse(b.remove(r));
            assertFalse(b.contains(r));
            assertEquals(0, b.countRooms());
        }
    }

    @Test
    public void testRemoveFacility() {
        Building b = new Building(facA, roomsA, buildName, buildID);

        // Try to remove each Facility in set B, an action which should not modify the collection
        int size = b.countFacilities();
        for (Facility f : facB) {
            assertFalse(b.contains(f));
            assertEquals(size, b.countFacilities());
        }

        // Remove each Facility from set A, asserting that the size decrements each time
        for (Facility f : facA) {
            assertTrue(b.remove(f));
            assertFalse(b.contains(f));
            assertEquals(--size, b.countFacilities());
        }
        assertEquals(0, b.countFacilities());

        // Re-remove each Facility from set A, asserting that it no longer modifies the collection
        for (Facility f : facA) {
            assertFalse(b.remove(f));
            assertFalse(b.contains(f));
            assertEquals(0, b.countFacilities());
        }
    }

    @Test
    public void testRemoveAllRooms() {
        Building b = new Building(facA, roomsA, buildName, buildID);
        b.addAllRooms(roomsB);

        // Assert that [b] initially contains all Rooms from groups A and B
        for (Room r : roomsA) {
            assertTrue(b.contains(r));
        }
        for (Room r : roomsB) {
            assertTrue(b.contains(r));
        }
        assertEquals(roomsA.size() + roomsB.size(), b.countRooms());

        // Remove all rooms from set A, ensuring that the Rooms from set B remain and the collection
        // is modified
        assertTrue(b.removeAllRooms(roomsA));
        for (Room r : roomsA) {
            assertFalse(b.contains(r));
        }
        for (Room r : roomsB) {
            assertTrue(b.contains(r));
        }
        assertEquals(roomsB.size(), b.countRooms());

        // Re-remove all Rooms from set A, ensuring that nothing changes
        assertFalse(b.removeAllRooms(roomsA));
        for (Room r : roomsA) {
            assertFalse(b.contains(r));
        }
        for (Room r : roomsB) {
            assertTrue(b.contains(r));
        }
        assertEquals(roomsB.size(), b.countRooms());
    }

    @Test
    public void testRemoveAllFacilities() {
        Building b = new Building(facA, roomsA, buildName, buildID);
        b.addAllFacilities(facB);

        // Assert that [b] initially contains all Facilities from groups A and B
        for (Facility f : facA) {
            assertTrue(b.contains(f));
        }
        for (Facility f : facB) {
            assertTrue(b.contains(f));
        }
        assertEquals(facA.size() + facB.size(), b.countFacilities());

        // Remove all Facilities from set A, ensuring that the rooms from set B remain and the
        // collection is modified
        assertTrue(b.removeAllFacilities(facA));
        for (Facility f : facA) {
            assertFalse(b.contains(f));
        }
        for (Facility f : facB) {
            assertTrue(b.contains(f));
        }
        assertEquals(facB.size(), b.countFacilities());

        // Re-remove all Facilities from set A, ensuring that nothing changes
        assertFalse(b.removeAllFacilities(facA));
        for (Facility f : facA) {
            assertFalse(b.contains(f));
        }
        for (Facility f : facB) {
            assertTrue(b.contains(f));
        }
        assertEquals(facB.size(), b.countFacilities());
    }

    @Test
    public void testRetainAllRooms() {
        Building b = new Building(facA, roomsA, buildName, buildID);
        b.addAllRooms(roomsB);

        // Assert that [b] initially contains all Rooms from groups A and B
        for (Room r : roomsA) {
            assertTrue(b.contains(r));
        }
        for (Room r : roomsB) {
            assertTrue(b.contains(r));
        }
        assertEquals(roomsA.size() + roomsB.size(), b.countRooms());

        // Retain all rooms from set A, ensuring that the Rooms from set A remain and the collection
        // is modified
        assertTrue(b.retainAllRooms(roomsA));
        for (Room r : roomsA) {
            assertTrue(b.contains(r));
        }
        for (Room r : roomsB) {
            assertFalse(b.contains(r));
        }
        assertEquals(roomsA.size(), b.countRooms());

        // Re-retain all Rooms from set A, ensuring that nothing changes
        assertFalse(b.retainAllRooms(roomsA));
        for (Room r : roomsA) {
            assertTrue(b.contains(r));
        }
        for (Room r : roomsB) {
            assertFalse(b.contains(r));
        }
        assertEquals(roomsA.size(), b.countRooms());
    }

    @Test
    public void testRetainAllFacilities() {
        Building b = new Building(facA, roomsA, buildName, buildID);
        b.addAllFacilities(facB);

        // Assert that [b] initially contains all Facilities from groups A and B
        for (Facility f : facA) {
            assertTrue(b.contains(f));
        }
        for (Facility f : facB) {
            assertTrue(b.contains(f));
        }
        assertEquals(facA.size() + facB.size(), b.countFacilities());

        // Retain all Facilities from set A, ensuring that the rooms from set A remain and the
        // collection is modified
        assertTrue(b.retainAllFacilities(facA));
        for (Facility f : facA) {
            assertTrue(b.contains(f));
        }
        for (Facility f : facB) {
            assertFalse(b.contains(f));
        }
        assertEquals(facA.size(), b.countFacilities());

        // Re-remove all Facilities from set A, ensuring that nothing changes
        assertFalse(b.retainAllFacilities(facA));
        for (Facility f : facA) {
            assertTrue(b.contains(f));
        }
        for (Facility f : facB) {
            assertFalse(b.contains(f));
        }
        assertEquals(facA.size(), b.countFacilities());
    }
}
