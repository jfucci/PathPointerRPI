package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by zyteka on 3/25/2017.
 */

public class CampusTest {
    private static Campus campus;
    @BeforeClass
    public static void init() {
        campus = new Campus("/src/test/java/com.nullpointers.pathpointer/TestData/TestNodes",
                "/src/test/java/com.nullpointers.pathpointer/TestData/0.csv");

    }

    @Test
    public void testGetBuildings() {
        Map<String, Integer> buildings = campus.getBuildings();
    }
}
