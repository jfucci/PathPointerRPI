package com.nullpointers.pathpointer;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Contains tests for the Campus class.
 *
 * @author Alexandra Zytek
 * Created on Wed, 2017-Mar-23
 */

public class CampusTest {
    private static final String NODES_DIRECTORY =
            "./src/test/java/com/nullpointers/pathpointer/TestData/TestNodes";
    private static final String EDGES_DIRECTORY =
            "./src/test/java/com/nullpointers/pathpointer/TestData/TestEdges";
    private static Campus campus;

    /**
     * Initialize the campus with the nodes and edges
     */
    @BeforeClass
    public static void init() {
        campus = new Campus(NODES_DIRECTORY, EDGES_DIRECTORY);
    }

    /**
     * Test the getBuildings() function
     */
    @Test
    public void testGetBuildings() {
        Map<String, Integer> buildings = campus.getBuildings();
        assertEquals(buildings.size(), 4);
        assertEquals(buildings.get("Test 1").intValue(), 1);
    }

    /**
     * Test the getRooms() function
     */
    @Test
    public void testGetRooms() {
        Map<String, Integer> rooms = campus.getRooms(1);
        assertEquals(rooms.size(), 1);
        assertEquals(rooms.get("Test 1").intValue(), 1);
    }

    /**
     * Test the getShortestPath
     */
    @Test
    public void testGetShortestPathSimple() {
        List<List<Location>> path = campus.getShortestPath(1, 4);
        assertEquals(path.get(0).get(0).getId().intValue(), 1);
        assertEquals(path.get(0).get(1).getId().intValue(), 4);
    }

    @Test
    public void testGetShortestPathHard() {
        List<List<Location>> path = campus.getShortestPath(1, 3);
        assertEquals(path.get(0).get(0).getId().intValue(), 1);
        assertEquals(path.get(0).get(1).getId().intValue(), 2);
        assertEquals(path.get(0).get(2).getId().intValue(), 3);
    }

    /**@Test
    public void testGetShortestPathMultipleFloorplans() {
        List<List<Location>> path = campus.getShortestPath(1, 5);
        assertEquals(path.get(0).get(0).getId().intValue(), 1);
        assertEquals(path.get(0).get(1).getId().intValue(), 2);
        assertEquals(path.get(1).get(0).getId().intValue(), 5);
    }*/
}
