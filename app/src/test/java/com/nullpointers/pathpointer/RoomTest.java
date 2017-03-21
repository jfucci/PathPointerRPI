package com.nullpointers.pathpointer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Anthony on 3/20/2017.
 */
public class RoomTest {
    @Test
    public void testDefaultConstructor() {
        Room r = new Room();
        assertNull(r.getName());
        assertNull(r.getBuilding());
    }

    @Test
    public void testConstructor() {
        Room r = new Room(1,2,2.2,3.3,"Hello", "World");
        assertEquals(r.getName(), "Hello");
        assertEquals(r.getBuilding(), "World");
    }
}
