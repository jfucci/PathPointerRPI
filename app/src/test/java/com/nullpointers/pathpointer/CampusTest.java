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
        assertEquals(4, buildings.size());
        assertEquals(1, buildings.get("Test 1").intValue());
    }

    /**
     * Test the getRooms() function
     */
    @Test
    public void testGetRooms() {
        Map<String, Integer> rooms = campus.getRooms(1);
        assertEquals(3, rooms.size());
        assertEquals(1, rooms.get("Test 1").intValue());
    }

    /**
     * Test the getShortestPath for a direct path
     */
    @Test
    public void testGetShortestPathSimple() {
        List<List<Location>> path = campus.getShortestPath(1, 4);
        assertEquals(1, path.get(0).get(0).getId().intValue());
        assertEquals(4, path.get(0).get(1).getId().intValue());
    }

    /**
     * Test the getShortestPath for an indirect path
     */
    @Test
    public void testGetShortestPathHard() {
        List<List<Location>> path = campus.getShortestPath(1, 3);
        assertEquals(1, path.get(0).get(0).getId().intValue());
        assertEquals(2, path.get(0).get(1).getId().intValue());
        assertEquals(3, path.get(0).get(2).getId().intValue());
    }

    /**
     * Test the getShortestPath for a path that has multiple floorplans
     */
    @Test
    public void testGetShortestPathMultipleFloorplans() {
        List<List<Location>> path = campus.getShortestPath(1, 1000001);
        assertEquals(1, path.get(0).get(0).getId().intValue());
        assertEquals(2, path.get(0).get(1).getId().intValue());
        assertEquals(1000001, path.get(1).get(0).getId().intValue());
    }
}
