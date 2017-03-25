package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests of equality and hash codes of different classes extending Location.
 * Equality checked against self and in both directions to validate reflexiveness,
 * transitivity, and symmetry.
 */
public class LocationInteractionTest {
    private static Intersection i1;
    private static Intersection i2;
    private static Intersection i3;
    private static Intersection i4;
    private static Room r1;
    private static Room r2;
    private static Room r3;
    private static Room r4;
    private static Facility f1;
    private static Facility f2;
    private static Facility f3;
    private static Facility f4;

    private static Intersection[] equalIntersections;
    private static Room[] equalRooms;
    private static Facility[] equalFacilities;
    private static Location[] equalLocations;

    private static Integer[] equalIntersectionsHashes;
    private static Integer[] equalRoomsHashes;
    private static Integer[] equalFacilitiesHashes;
    private static Integer[] equalLocationsHashes;

    private static Integer i3Hash;
    private static Integer r3Hash;
    private static Integer f3Hash;

    private static Object obj;
    private static Integer objHash;

    /**
     * This method asserts that all elements in an array are
     * equal under the equals() method in both directions
     *
     * @param arr The array whose elements should be compared
     * @param <T> The type of elements in arr
     */
    private static <T> void assertAllEqual(T[] arr) {
        for (T t1 : arr) {
            for (T t2 : arr) {
                assertEquals(t1,t2);
            }
        }
    }

    /**
     * This method asserts that all elements in an array are
     * not equal under the equals() method in both directions
     * to the other provided object
     *
     * @param arr The array whose elements should be compared
     * @param <T1> The type of elements in arr
     * @param <T2> The type of elem
     */
    private static <T1,T2> void assertAllNotEqual(T1[] arr, T2 elem) {
        for (T1 t : arr) {
            assertNotEquals(t,elem);
            assertNotEquals(elem,t);
        }
    }

    @BeforeClass
    public static void init() {
        i1 = new Intersection(1,2,3.3,4.4);
        i2 = new Intersection(1,23,3.6,4.48);
        i3 = new Intersection(2,2,3.3,4.4);
        i4 = new Intersection(7, 2,3.3,4.4);
        r1 = new Room(3,2,3.3,4.4,"a","b");
        r2 = new Room(3,2,3.34,4.44,"af","bd");
        r3 = new Room(4,2,3.3,4.4,"a","b");
        r4 = new Room(7, 2,3.3,4.4, "aa","bb");
        f1 = new Facility(5,2,3.3,4.4,FacilityType.Bathroom);
        f2 = new Facility(5,32,3.34,4.43,FacilityType.VendingMachine);
        f3 = new Facility(6,32,3.34,4.43,FacilityType.VendingMachine);
        f4 = new Facility(7, 2,3.3,4.4, FacilityType.Bathroom);

        equalIntersections = new Intersection[] {i1,i2};
        equalRooms = new Room[] {r1,r2};
        equalFacilities = new Facility[] {f1,f2};
        equalLocations = new Location[] {i4,r4,f4};

        equalIntersectionsHashes = new Integer[] {i1.hashCode(), i2.hashCode()};
        equalRoomsHashes = new Integer[] {r1.hashCode(), r2.hashCode()};
        equalFacilitiesHashes = new Integer[] {f1.hashCode(), f2.hashCode()};
        equalLocationsHashes = new Integer[] {i4.hashCode(), r4.hashCode(), f4.hashCode()};

        i3Hash = i3.hashCode();
        r3Hash = r3.hashCode();
        f3Hash = f3.hashCode();

        obj = new Object();
        objHash = obj.hashCode();
    }

    @Test
    public void testEquals() {
        assertAllEqual(equalIntersections);
        assertAllEqual(equalRooms);
        assertAllEqual(equalFacilities);
        assertAllEqual(equalFacilities);
    }

    @Test
    public void testNotEquals() {
        assertAllNotEqual(equalIntersections, i3);
        assertAllNotEqual(equalRooms, r3);
        assertAllNotEqual(equalFacilities, f3);
        assertAllNotEqual(equalIntersections, obj);
        assertAllNotEqual(equalRooms, obj);
        assertAllNotEqual(equalFacilities, obj);
    }

    @Test
    public void testSameHash() {
        assertAllEqual(equalIntersectionsHashes);
        assertAllEqual(equalRoomsHashes);
        assertAllEqual(equalFacilitiesHashes);
        assertAllEqual(equalLocationsHashes);
    }

    @Test
    public void testDifferentHash() {
        assertAllNotEqual(equalIntersectionsHashes, i3Hash);
        assertAllNotEqual(equalRoomsHashes, r3Hash);
        assertAllNotEqual(equalFacilitiesHashes, f3Hash);
        assertAllNotEqual(equalIntersectionsHashes, objHash);
        assertAllNotEqual(equalRoomsHashes, objHash);
        assertAllNotEqual(equalFacilitiesHashes, objHash);
    }
}
