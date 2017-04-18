package com.nullpointers.pathpointer;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the Schedule class
 *
 * @author Matthew R. Dobbins
 * Created on Wed, 2017-Apr-16
 */
public class ScheduleTest {

    private static Schedule sched;
    private static Event e1;
    private static Event e2;
    private static Event e3;
    private static Event e4;
    private static Event e1bad;
    private static Event e2bad;
    private static Event e3bad;
    private static Event e4bad;


    @BeforeClass
    public static void initialize() {
        // Obtains an instance of the Schedule to work with
        sched = Schedule.getInstance();

        // All occurrences take place in one of these four rooms
        Room r1 = new Room(457, 17, 0.0, 0.0, "Charms Room", "Hogwarts");
        Room r2 = new Room(458, 17, 0.0, 1.0, "Potions Room", "Hogwarts");
        Room r3 = new Room(459, 17, 1.0, 1.0, "Transfiguration Room", "Hogwarts");
        Room r4 = new Room(460, 17, 1.0, 0.0, "Astronomy Tower", "Hogwarts");

        // Days of week for each occurrence
        boolean[] dowMR = new boolean[]{true, false, false, true, false, false, false};
        boolean[] dowTF = new boolean[]{false, true, false, false, true, false, false};
        boolean[] dowW = new boolean[]{false, false, true, false, false, false, false};
        boolean[] dowMWR = new boolean[]{true, false, true, true, false, false, false};
        boolean[] dowTWF = new boolean[]{false, true, true, false, true, false, false};

        // Individual occurrences, none of which overlap
        Occurrence o1 = new Occurrence(dowMR, new Time(8, 0), new Time(9, 50), r1);
        Occurrence o2 = new Occurrence(dowTF, new Time(8, 0), new Time(9, 50), r2);
        Occurrence o3 = new Occurrence(dowW, new Time(8, 0), new Time(9, 50), r3);
        Occurrence o4 = new Occurrence(dowMWR, new Time(10, 0), new Time(10, 50), r4);
        Occurrence o5 = new Occurrence(dowTF, new Time(10, 0), new Time(10, 50), r1);
        Occurrence o6 = new Occurrence(dowMR, new Time(11, 0), new Time(11, 50), r2);
        Occurrence o7 = new Occurrence(dowTWF, new Time(11, 0), new Time(11, 50), r3);
        Occurrence o8 = new Occurrence(dowMR, new Time(12, 0), new Time(13, 50), r4);
        Occurrence o9 = new Occurrence(dowTF, new Time(12, 0), new Time(13, 50), r1);
        Occurrence o10 = new Occurrence(dowW, new Time(12, 0), new Time(13, 50), r2);
        Occurrence o11 = new Occurrence(dowMWR, new Time(14, 0), new Time(14, 50), r3);
        Occurrence o12 = new Occurrence(dowTF, new Time(14, 0), new Time(15, 50), r4);

        // Sets of occurrences, none of which overlap
        Set<Occurrence> so1 = new HashSet<>();
        so1.add(o1);
        so1.add(o5);
        so1.add(o9);

        Set<Occurrence> so2 = new HashSet<>();
        so2.add(o2);
        so2.add(o6);
        so2.add(o10);

        Set<Occurrence> so3 = new HashSet<>();
        so3.add(o3);
        so3.add(o7);
        so3.add(o11);

        Set<Occurrence> so4 = new HashSet<>();
        so4.add(o4);
        so4.add(o8);
        so4.add(o12);

        // Sets of occurrences that do not overlap internally but overlap with one another
        Set<Occurrence> so1bad = new HashSet<>();
        so1bad.add(o1);
        so1bad.add(o2);
        so1bad.add(o3);

        Set<Occurrence> so2bad = new HashSet<>();
        so2bad.add(o1);
        so2bad.add(o2);
        so2bad.add(o4);

        Set<Occurrence> so3bad = new HashSet<>();
        so3bad.add(o1);
        so3bad.add(o3);
        so3bad.add(o4);

        Set<Occurrence> so4bad = new HashSet<>();
        so4bad.add(o1);
        so4bad.add(o2);
        so4bad.add(o3);

        // Events based on these sets of Occurrenes
        e1 = new Event("Charms", so1);
        e2 = new Event("Potions", so2);
        e3 = new Event("Transfiguration", so3);
        e4 = new Event("Astronomy", so4);

        e1bad = new Event("Harry Skips Class", so1bad);
        e2bad = new Event("Harry Gets Detention", so2bad);
        e3bad = new Event("Harry Loses Bones in his Arm", so3bad);
        e4bad = new Event("Dementors Attack School", so4bad);

        // Assert that the schedule file contains e1-e4
        System.out.println(sched.size());
        System.out.println(sched.contains(e1));
        System.out.println(sched.contains(e2));
        System.out.println(sched.contains(e3));
        System.out.println(sched.contains(e4));
    }

    ///////////////////////////
    // BEGIN BLACK BOX TESTS //
    ///////////////////////////

    @Test
    public void testSingletonBehavior() {
        // Tests that only one instance of this class is ever created
        Schedule s2 = Schedule.getInstance();
        assertTrue(sched == s2);
    }

    @Test
    public void testContains() {
        sched.clear();

        // Initially, the Schedule contains no events
        assertFalse(sched.contains(e1));
        assertFalse(sched.contains(e2));
        assertFalse(sched.contains(e3));
        assertFalse(sched.contains(e4));

        // Add some events and check that the schedule contains them
        sched.add(e1);
        sched.add(e2);

        assertTrue(sched.contains(e1));
        assertTrue(sched.contains(e2));
        assertFalse(sched.contains(e3));
        assertFalse(sched.contains(e4));

        // Add some more events and check that the schedule contains them
        sched.add(e3);
        sched.add(e4);

        assertTrue(sched.contains(e1));
        assertTrue(sched.contains(e2));
        assertTrue(sched.contains(e3));
        assertTrue(sched.contains(e4));
    }

    @Test
    public void testSize() {
        sched.clear();

        // Initially, the Schedule contains no events
        assertEquals(0, sched.size());

        // Add some events and check that the schedule counts them
        sched.add(e1);
        sched.add(e2);
        assertEquals(2, sched.size());

        // Add some more events and check that the schedule counts them
        sched.add(e3);
        sched.add(e4);
        assertEquals(4, sched.size());
    }

    @Test
    public void testIsEmpty() {
        sched.clear();

        // Initially, the Schedule contains no events
        assertTrue(sched.isEmpty());

        // Add some events and check that the schedule counts them
        sched.add(e1);
        assertFalse(sched.isEmpty());

        // Add some more events and check that the schedule counts them
        sched.add(e2);
        sched.add(e3);
        sched.add(e4);
        assertFalse(sched.isEmpty());
    }

    @Test
    public void testClear() {
        sched.add(e1);
        sched.add(e2);
        sched.add(e3);
        sched.add(e4);

        // The clear() function should remove all tasks from the schedule
        assertFalse(sched.isEmpty());
        assertNotEquals(0, sched.size());
        sched.clear();
        assertTrue(sched.isEmpty());
        assertEquals(0, sched.size());
    }

    @Test
    public void testIterator() {
        // Add some events to the schedule
        Set<Event> events = new HashSet<>();
        events.add(e1);
        events.add(e2);
        events.add(e3);
        events.add(e4);

        sched.clear();
        sched.add(e1);
        sched.add(e2);
        sched.add(e3);
        sched.add(e4);

        // Assert that the iterator covers exactly the Events in the set above
        Iterator<Event> i = sched.iterator();
        while (i.hasNext()) {
            Event e = i.next();
            assertTrue(events.contains(e));
            events.remove(e);
        }
        assertEquals(0, events.size());
    }

    @Test
    public void testAdd() {
        sched.clear();
        int expectedSize = 0;

        // The addition of four non-overlapping events should return true and increment the size
        // every time
        ++expectedSize;
        assertTrue(sched.add(e1));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e1));

        ++expectedSize;
        assertTrue(sched.add(e2));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e2));

        ++expectedSize;
        assertTrue(sched.add(e3));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e3));

        ++expectedSize;
        assertTrue(sched.add(e4));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e4));

        // Re-adding events should change nothing
        assertFalse(sched.add(e1));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e1));

        assertFalse(sched.add(e2));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e2));

        assertFalse(sched.add(e3));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e3));

        assertFalse(sched.add(e4));
        assertEquals(expectedSize, sched.size());
        assertTrue(sched.contains(e4));

        // If four overlapping events are added, only the first should be accepted
        sched.clear();
        assertEquals(0, sched.size());

        assertTrue(sched.add(e1bad));
        assertEquals(1, sched.size());
        assertTrue(sched.contains(e1bad));

        assertFalse(sched.add(e2bad));
        assertEquals(1, sched.size());
        assertFalse(sched.contains(e2bad));

        assertFalse(sched.add(e3bad));
        assertEquals(1, sched.size());
        assertFalse(sched.contains(e3bad));

        assertFalse(sched.add(e4bad));
        assertEquals(1, sched.size());
        assertFalse(sched.contains(e4bad));
    }

    @Test
    public void testRemove() {
        sched.clear();
        sched.add(e1);
        sched.add(e2);
        int expectedSize = sched.size();

        // Removing events that are not there should do nothing
        assertFalse(sched.contains(e3));
        assertFalse(sched.remove(e3));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e3));

        assertFalse(sched.contains(e4));
        assertFalse(sched.remove(e4));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e4));

        // Removing events that are there should decrease size
        --expectedSize;
        assertTrue(sched.contains(e1));
        assertTrue(sched.remove(e1));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e1));

        --expectedSize;
        assertTrue(sched.contains(e2));
        assertTrue(sched.remove(e2));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e2));

        // Re-removing events should do nothing
        assertFalse(sched.contains(e1));
        assertFalse(sched.remove(e1));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e1));

        assertFalse(sched.contains(e2));
        assertFalse(sched.remove(e2));
        assertEquals(expectedSize, sched.size());
        assertFalse(sched.contains(e2));
    }

    @AfterClass
    public static void terminate() {
        // Write events e1-e4 to the file for load testing
        sched.clear();
        sched.add(e1);
        sched.add(e2);
        sched.add(e3);
        sched.add(e4);
    }
}
