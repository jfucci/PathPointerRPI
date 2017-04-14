package com.nullpointers.pathpointer;

import org.junit.Test;
import org.junit.BeforeClass;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Occurrence.
 */
public class OccurrenceTest {
    private static Occurrence o1;
    private static Occurrence o2;
    private static Occurrence o3;
    private static Occurrence o4;
    private static Occurrence o5;
    private static Occurrence o6;
    private static Occurrence o7;
    private static Occurrence o8;
    private static Occurrence o9;
    private static Occurrence o10;
    private static Occurrence o11;

    private static Occurrence[] occurrences;

    private static boolean[] d1;
    private static boolean[] d2;
    private static boolean[] d3;
    private static boolean[] d4;

    private static Time s1;
    private static Time s2;
    private static Time s3;
    private static Time s4;
    private static Time s5;

    private static Time e1;
    private static Time e2;
    private static Time e3;
    private static Time e4;

    @BeforeClass
    public static void init() {
        d1 = new boolean[]{true,false,false,true,false,false,false};
        d2 = new boolean[]{true,false,false,true,false,false,false};
        d3 = new boolean[]{true,false,true,true,false,false,false};
        d4 = new boolean[]{false,false,false,false,false,false,true};

        s1 = new Time(12,0);
        s2 = new Time(12,0);
        s3 = new Time(16,0);
        s4 = new Time(11,30);
        s5 = new Time(13,50);

        e1 = new Time(13,50);
        e2 = new Time(13,50);
        e3 = new Time(17,50);
        e4 = new Time(14,30);

        o1 = new Occurrence(d1,s1,e1);
        o2 = new Occurrence(d2,s2,e2);
        o3 = new Occurrence(d1,s3,e1);
        o4 = new Occurrence(d1,s1,e3);
        o5 = new Occurrence(d1,s3,e3);
        o6 = new Occurrence(d3,s3,e1);
        o7 = new Occurrence(d3,s1,e3);
        o8 = new Occurrence(d3,s3,e3);
        o9 = new Occurrence(d4,s1,e1);
        o10 = new Occurrence(d1,s4,e4);
        o11 = new Occurrence(d1,s5,e4);

        occurrences = new Occurrence[]{o1,o3,o4,o5,o6,o7,o8};
    }


    @Test
    public void testConstructor() {
        boolean[] daysOfWeek = {false, true, true, false,true, false, false};
        Occurrence o = new Occurrence(daysOfWeek,new Time(12,0),new Time(14,0) );
        assertArrayEquals(o.getDaysOfWeek(), new boolean[]{false, true, true, false,true, false, false});
        assertEquals(o.getStart(), new Time(12,0));
        assertEquals(o.getEnd(), new Time(14,0));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalDaysofWeek() {
        boolean[] daysOfWeek = {true,false};
        Occurrence o = new Occurrence(daysOfWeek,new Time(0,0),new Time(1,0));
    }

    // Test positive and negative equals and hash code

    @Test
    public void testEquals() {
        assertEquals(o1,o2);
    }

    @Test
    public void testNotEquals() {
        for (int i=0; i<occurrences.length ; ++i) {
            for (int j=0; j<occurrences.length; ++j) {
                //occurrences[i] is the same thing as occurrences[j] if i==j
                if (i == j) continue;
                else assertNotEquals(occurrences[i],occurrences[j]);
            }
        }
    }

    @Test
    public void testEqualHash() {
        assertEquals(o1.hashCode(),o2.hashCode());
    }

    @Test
    public void testNotEqualHash() {
        for (int i=0; i<occurrences.length ; ++i) {
            for (int j=0; j<occurrences.length; ++j) {
                //occurrences[i] is the same thing as occurrences[j] if i==j
                if (i == j) continue;
                else assertNotEquals(occurrences[i].hashCode(),occurrences[j].hashCode());
            }
        }
    }

    @Test
    public void testOverlaps() {
        assertTrue(o1.overlaps(o1));
        assertFalse(o1.overlaps(o5));
        assertTrue(o1.overlaps(o2));
        assertFalse(o1.overlaps(o9));
        assertTrue(o1.overlaps(o10));
        assertTrue(o10.overlaps(o1));
        assertFalse(o1.overlaps(o11));
        assertFalse(o11.overlaps(o1));
    }
}
