package com.nullpointers.pathpointer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Facility unit tests.
 */
public class FacilityTest {
    @Test
    public void testDefaultConstructor() {
        Facility f = new Facility();
        assertNull(f.getType());
    }

    @Test
    public void testConstructor() {
        Facility f = new Facility(10,42,3.14,7.77, FacilityType.Bathroom);
        assertEquals(f.getType(), FacilityType.Bathroom);
    }
}
