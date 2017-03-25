package com.nullpointers.pathpointer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Created by zyteka on 3/20/2017.
 */
public class Campus {
    Map<Integer, Building> buildings;
    Map<String, Integer> buildingsNameToId;
    Graph<Location, DefaultWeightedEdge> campusGraph;
    Map<Integer, Location> locations;

    public Campus(String nodesFolder, String edgesFolder) {
        buildings = new HashMap<>();
        buildingsNameToId = new HashMap<>();
        campusGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        locations = new HashMap<>();
        File nodeDir = new File(nodesFolder);
        File[] nodeFiles = nodeDir.listFiles();
        if (nodeFiles != null) {
            for (File nodeFile : nodeFiles) {
                int floorplan;
                try {
                    floorplan = Integer.parseInt(nodeFile.getName().split(".")[0]);
                } catch(NumberFormatException nfe) {
                    floorplan = -1;
                }
                addNodes(nodeFile, floorplan);
            }
        } else {
            throw new IllegalArgumentException("Invalid nodes folder for campus");
        }
        File edgeDir = new File(nodesFolder);
        File[] edgeFiles = edgeDir.listFiles();
        if (edgeFiles != null) {
            for (File edgeFile : edgeFiles) {
                int floorplan;
                try {
                    floorplan = Integer.parseInt(edgeFile.getName().split(".")[0]);
                } catch(NumberFormatException nfe) {
                    floorplan = -1;
                }
                addNodes(edgeFile, floorplan);
            }
        } else {
            throw new IllegalArgumentException("Invalid edge folder for campus");
        }


    }

    private void addNodes(File nodeInput, int floorplan) {
        try (BufferedReader br = new BufferedReader(new FileReader(nodeInput))) {
            String nextLocation;
            while ((nextLocation = br.readLine()) != null) {
                String[] details = nextLocation.split(",");
                String buildingName = details[0];
                int buildingID = Integer.parseInt(details[1]);
                double xCoord = Integer.parseInt(details[2]);
                double yCoord = Integer.parseInt(details[3]);
                if(!buildingName.equals("")) {  //ignore intersections
                    Room room = new Room(buildingID, floorplan, xCoord, yCoord, buildingName, buildingName);
                    Set<Room> rooms = new HashSet<>();
                    rooms.add(room);
                    Building building = new Building(null, rooms, buildingName, buildingID);
                    buildings.put(buildingID, building);
                    buildingsNameToId.put(buildingName, buildingID);
                    campusGraph.addVertex(room);
                    locations.put(buildingID, room);
                }
                else {
                    Intersection intersection = new Intersection(buildingID, floorplan, xCoord, yCoord);
                    campusGraph.addVertex(intersection);
                    locations.put(buildingID, intersection);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void addEdges(File edgeInput, int floorplan) {
        try (BufferedReader br = new BufferedReader(new FileReader(edgeInput))) {
            String nextEdge;
            while((nextEdge = br.readLine()) != null) {
                String[] details = nextEdge.split(",");
                int firstLocation = Integer.parseInt(details[0]);
                int secondLocation = Integer.parseInt(details[1]);
                campusGraph.addEdge(locations.get(firstLocation), locations.get(secondLocation));
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * @return a map of all buildings mapped to their IDs
     */
    public Map<String, Integer> getBuildings() {
        return buildingsNameToId;
    }

    /**
     * Returns a map for all room, id pairings for this building
     * @param buildingId the building for which to get rooms
     * @return a mapping from room to room ID for all rooms in this building
     */
    public Map<String, Integer> getRooms(int buildingId) {
        Building building = buildings.get(buildingId);
        Iterator<Room> roomIter = building.roomIterator();
        Map<String, Integer> rooms = new HashMap<>();
        while(roomIter.hasNext()) {
            Room nextRoom = roomIter.next();
            rooms.put(nextRoom.getName(), nextRoom.getId());
        }
        return rooms;
    }

    /**
     * Get the shortest path between the provided locations
     * @param start ID of the first location
     * @param end ID of the second location
     * @return a GraphPath representing the shortest path between locations
     */
    public List<List<Location>> getShortestPath(int start, int end) {
        Location startLocation = locations.get(start);
        Location endLocation = locations.get(end);

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(campusGraph);
        GraphPath shortestPath = dijkstraShortestPath.getPath(startLocation, endLocation);
        List<Location> path = shortestPath.getVertexList();
        if(path.size() == 0) {
            //ERROR CHECK
            return null;
        }
        List<List<Location>> segmentedPath = new ArrayList<>();
        int currentFloorplan = path.get(0).getFloorPlan();
        int i = 0;
        while(i < path.size()) {
            List<Location> nextSegment = new ArrayList<>();
            while(i < path.size() && path.get(i).getFloorPlan() == currentFloorplan) {
                nextSegment.add(path.get(i));
                i++;
            }
            segmentedPath.add(nextSegment);
        }
        return segmentedPath;
    }
}