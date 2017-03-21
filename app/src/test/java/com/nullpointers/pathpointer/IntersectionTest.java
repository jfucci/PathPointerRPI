package com.nullpointers.pathpointer;

import org.junit.Test;

/**
 * Currently Intersection does not add any functionality to Location.
 * Therefore only the ability to instantiate Intersection is tested.
 */
public class IntersectionTest {
    @Test
    public void testDefaultConstructor() {
        Intersection i = new Intersection();
    }

    @Test
    public void testConstructor() {
        Intersection i = new Intersection(1,2,3.3,4.4);
    }
}
