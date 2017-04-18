package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for Time class.
 */
public class TimeTest {
    static private Time t1;
    static private Time t2;
    static private Time t3;
    static private Time t4;
    static private Time t5;
    static private Time t6;

    @BeforeClass
    public static void init() {
        t1 = new Time (1,2);
        t2 = new Time(1,2);
        t3 = new Time(1,0);
        t4 = new Time(0,2);
        t5 = new Time(2, 30);
        t6 = new Time(3, 0);
    }

    @Test
    public void testTime() {
        assertEquals(t1.getHour(),1);
        assertEquals(t1.getMinute(),2);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLowHours() {
        Time t = new Time(-1,0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testHighHours() {
        Time t = new Time(24,0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testLowMinutes() {
        Time t = new Time(0,-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testHighMinutes() {
        Time t = new Time(0,60);
    }

    @Test
    public void testToString() {
        Time t = new Time(5,10);
        assertEquals(t.toString(),"05:10");
    }

    @Test
    public void testEquals() {
        assertEquals(t1,t2);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(t1,t3);
        assertNotEquals(t1,t4);
        assertNotEquals(t3,t4);
    }

    @Test
    public void testHashEquals() {
        assertEquals(t1.hashCode(),t2.hashCode());
    }

    @Test
    public void testHashNotEquals(){
        assertNotEquals(t1.hashCode(),t3.hashCode());
        assertNotEquals(t1.hashCode(),t4.hashCode());
        assertNotEquals(t3.hashCode(),t4.hashCode());
    }

    @Test
    public void testIsLaterThan() {
        assertTrue(t1.isLaterThan(t2));
        assertTrue(t1.isLaterThan(t3));
        assertFalse(t3.isLaterThan(t1));
        assertTrue(t6.isLaterThan(t5));
        assertFalse(t5.isLaterThan(t6));
    }

    @Test
    public void testIsEarlierThan() {
        assertFalse(t1.isEarlierThan(t2));
        assertFalse(t1.isEarlierThan(t3));
        assertTrue(t3.isEarlierThan(t1));
        assertFalse(t6.isEarlierThan(t5));
        assertTrue(t5.isEarlierThan(t6));
    }
}
