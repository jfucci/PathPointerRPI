package com.nullpointers.pathpointer;

import android.content.Context;
import android.content.res.AssetManager;

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
 * The Campus class contains information about all the buildings on campus, and has the graph for
 * the campus. It contains functionality for finding the shortest path between locations on campus.
 *
 * @author Alexandra Zytek
 * Created on Wed, 2017-Mar-23
 */

public class Campus {
    Map<Integer, Building> buildings;
    Map<String, Integer> buildingsNameToId;
    Graph<Location, DefaultWeightedEdge> campusGraph;
    Map<Integer, Location> locations;

    /**
     * Makes a new Campus object that will take from the given nodes and edge folders
     * @param nodesFolder a directory that contains folders with node data
     * @param edgesFolder a directory that contains folders with edge data
     */
    public Campus(Context context, String nodesFolder, String edgesFolder) {
        buildings = new HashMap<>();
        buildingsNameToId = new HashMap<>();
        campusGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        locations = new HashMap<>();
        AssetManager assetManager = context.getAssets();
        String[] nodeFiles = null;
        String[] edgeFiles = null;

        try {
            nodeFiles = assetManager.list(nodesFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            edgeFiles = assetManager.list(edgesFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (nodeFiles != null) {
            for (String nodeFile : nodeFiles) {
                int floorplan;
                try {
                    floorplan = Integer.parseInt(nodeFile.split("\\.")[0]);
                } catch(NumberFormatException nfe) {
                    floorplan = -1;
                }

                InputStream nodeFileStream = null;
                try {
                    nodeFileStream = assetManager.open(nodesFolder + '/' + nodeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(nodeFileStream != null) {
                    addNodes(nodeFileStream, floorplan);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid nodes folder for campus");
        }

        if (edgeFiles != null) {
            for (String edgeFile : edgeFiles) {
                int floorplan;
                try {
                    floorplan = Integer.parseInt(edgeFile.split("\\.")[0]);
                } catch(NumberFormatException nfe) {
                    floorplan = -1;
                }

                InputStream edgeFileStream = null;
                try {
                    edgeFileStream = assetManager.open(edgesFolder + '/' + edgeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(edgeFileStream != null) {
                    addEdges(edgeFileStream);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid edge folder for campus");
        }
    }

    /**
     * Add the nodes from the nodeInput file to the graph
     * @param nodeInput the file from which to retrieve the nodes
     * @param floorplan the floorplan of all the nodes being added
     */
    private void addNodes(InputStream nodeInput, int floorplan) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(nodeInput))) {
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
                    if(floorplan == 0) {
                        Building building = new Building(null, rooms, buildingName, buildingID);
                        buildings.put(buildingID, building);
                        buildingsNameToId.put(buildingName, buildingID);
                    }
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
            System.out.println("Invalid nodes file");
        } catch (IOException io) {
            System.out.println("IOException while opening file in addNodes");
        }
    }

    /**
     * Add edges to the graph.
     * @param edgeInput the file from which to get the edge data
     */
    private void addEdges(InputStream edgeInput) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(edgeInput))) {
            String nextEdge;
            while((nextEdge = br.readLine()) != null) {
                String[] details = nextEdge.split(",");
                int firstLocation = Integer.parseInt(details[0]);
                int secondLocation = Integer.parseInt(details[1]);
                campusGraph.addEdge(locations.get(firstLocation), locations.get(secondLocation));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Invalid edge file");
        } catch (IOException io) {
            System.out.println("IOException while opening file in addEdges");
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
            System.out.println("No path exists");
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
