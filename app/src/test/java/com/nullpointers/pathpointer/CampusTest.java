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
    private static final String NODES_DIRECTORY =
            "C:\\Users\\zyteka\\AndroidStudioProjects\\PathPointerRPI\\" +
                    "app\\src\\test\\java\\com\\nullpointers\\pathpointer\\TestData\\TestNodes";
    private static final String EDGES_DIRECTORY =
            "C:\\Users\\zyteka\\AndroidStudioProjects\\PathPointerRPI\\" +
                    "app\\src\\test\\java\\com\\nullpointers\\pathpointer\\TestData\\TestEdges";
    private static Campus campus;
    @BeforeClass
    public static void init() {
        //campus = new Campus(NODES_DIRECTORY, EDGES_DIRECTORY);

    }

    @Test
    public void testGetBuildings() {
        Map<String, Integer> buildings = campus.getBuildings();
    }
}
