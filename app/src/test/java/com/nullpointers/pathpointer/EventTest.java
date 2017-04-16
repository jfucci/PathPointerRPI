package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Event class.
 */
public class EventTest {

    //occurrences take place in one of these two rooms
    private static Room l1;
    private static Room l2;

    //e1 and e2 have one occurrence each that overlap each other
    private static Event e1;
    private static Event e2;

    //e3 and e4 have multiple occurrences, some of which overlap each other
    private static Event e3;
    private static Event e4;

    //e5 and e6 do not overlap any other events
    private static Event e5;
    private static Event e6;

    //o1 and o2 contain occurrences that overlap
    private static Set<Occurrence> o1;
    private static Set<Occurrence> o2;

    //o3 and o4 contain occurrences that do not overlap with anyone else
    private static Set<Occurrence> o3;
    private static Set<Occurrence> o4;

    @BeforeClass
    public static void init() {
        l1 = new Room(0, 1, 0.0, 1.0, "318", "DCC");
        l2 = new Room(1, 1, 1.0, 0.0, "324", "DCC");

        o1 = new HashSet<>();
        o2 = new HashSet<>();
        o3 = new HashSet<>();
        o4 = new HashSet<>();

        boolean[] days1 = {true, false, false, false, false, false, false};
        boolean[] days2 = {false, true, false, false, false, false, false};

        o1.add(new Occurrence(days1, new Time(12,0), new Time(14,0), l1));
        o2.add(new Occurrence(days1, new Time(11,0), new Time(13,0), l2));

        e1 = new Event("Event 1", o1);
        e2 = new Event("Event 2", o2);

        o1.add(new Occurrence(days1, new Time(16,0), new Time(17,0), l1));
        o2.add(new Occurrence(days1, new Time(17,0), new Time(18,0), l2));

        e3 = new Event("Event 3", o1);
        e4 = new Event("Event 4", o2);

        o3.add(new Occurrence(days2, new Time(12,0), new Time(14,0), l1));
        o3.add(new Occurrence(days1, new Time(10,0), new Time(11,0), l2));
        o4.add(new Occurrence(days1, new Time(14,0), new Time(16,0), l1));

        e5 = new Event("Event 5", o3);
        e6 = new Event("Event 6", o4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalEvent() {
        boolean[] days1 = {true, false, false, false, false, false, false};
        Set<Occurrence> occurrences = new HashSet<>();
        occurrences.add(new Occurrence(days1, new Time(8,0), new Time(10,0), l1));
        occurrences.add(new Occurrence(days1, new Time(9,0), new Time(10,0), l2));
        new Event("Illegal Event", occurrences);

    }

    @Test
    public void testGetName() {
        assertTrue(e1.getName().equals("Event 1"));
    }

    @Test
    public void testIterator() {
        Iterator<Occurrence> itr = e3.occurIterator();
        Iterator<Occurrence> itr2 = o1.iterator();
        assertTrue(itr.next().equals(itr2.next()));
        assertTrue(itr.next().equals(itr2.next()));
    }

    @Test
    public void testNumberOfOccurrences() {
        assertEquals(2, e3.numberOfOccurrences());
        assertEquals(1, e1.numberOfOccurrences());
    }

    @Test
    public void testContainsOccurrence() {
        assertTrue(e3.containsOccurrence(o1.iterator().next()));
    }

    @Test
    public void testOverlaps() {
        assertTrue(e1.overlaps(e2));
        assertTrue(e2.overlaps(e1));
        assertTrue(e1.overlaps(e3));
        assertTrue(e1.overlaps(e4));
        assertTrue(e3.overlaps(e4));
        assertTrue(e4.overlaps(e3));
        assertFalse(e1.overlaps(e5));
        assertFalse(e5.overlaps(e2));
        assertFalse(e6.overlaps(e5));
        assertFalse(e5.overlaps(e6));
    }
}
