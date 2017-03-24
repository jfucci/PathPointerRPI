package com.nullpointers.pathpointer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.mockito.Mockito;

/**
 * Unit Tests on Location ABC
 */
public class LocationTest {

    @Test
    public void testDefaultConstructor() {
        Location loc = Mockito.mock(Location.class, Mockito.CALLS_REAL_METHODS);
        assertNull(loc.getId());
        assertNull(loc.getFloorPlan());
        assertNull(loc.getX());
        assertNull(loc.getY());
    }

   @Test
    public void testConstructor() {
        Location loc = Mockito.spy(new Location(10,42,3.14,7.77) {});
        assertEquals(loc.getFloorPlan(), Integer.valueOf(42));
        assertEquals(loc.getX(), Double.valueOf(3.14));
        assertEquals(loc.getY(), Double.valueOf(7.77));
    }
}