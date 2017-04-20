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
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
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
    //The amount that will be added to the ID's of each floorplan
    public static final int ID_MOD = 1000000;

    private Map<Integer, Building> buildings;
    private Map<String, Integer> buildingsNameToId;
    private Graph<Location, DefaultWeightedEdge> campusGraph;
    private Map<Integer, Location> locations;

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
            //Find all campus files (X.0.0.csv), and add the buildings from them
            for(String nodeFile : nodeFiles) {
                String[] fileNameElements = nodeFile.split("\\.");
                int building = Integer.parseInt(fileNameElements[1]);
                if(building == 0) {
                    InputStream nodeFileStream = null;
                    try {
                        nodeFileStream = assetManager.open(nodesFolder + '/' + nodeFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(nodeFileStream != null) {
                        addBuildings(nodeFileStream);
                    }
                }
            }
            for (String nodeFile : nodeFiles) {
                int floorplan;
                int buildingId;
                try {
                    String[] fileNameElements = nodeFile.split("\\.");
                    floorplan = Integer.parseInt(fileNameElements[0]);
                    buildingId = Integer.parseInt(fileNameElements[1]);
                } catch(NumberFormatException nfe) {
                    floorplan = -1;
                    buildingId = -1;
                }

                InputStream nodeFileStream = null;
                try {
                    nodeFileStream = assetManager.open(nodesFolder + '/' + nodeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(nodeFileStream != null) {
                    addNodes(nodeFileStream, floorplan, buildingId);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid nodes folder for campus");
        }

        if (edgeFiles != null) {
            for (String edgeFile : edgeFiles) {
                InputStream edgeFileStream = null;
                try {
                    edgeFileStream = assetManager.open(edgesFolder + '/' + edgeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(edgeFileStream != null) {
                    String[] fileNameElements = edgeFile.split("\\.");
                    if(fileNameElements[0].equals("X")) {
                        addEdgesCross(edgeFileStream);
                    }
                    else {
                        addEdges(edgeFileStream, Integer.parseInt(fileNameElements[0]));
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid edge folder for campus");
        }
    }

    /**
     * Add all buildings to the building map
     * @param nodeInput a campus node input file
     */
    private void addBuildings(InputStream nodeInput) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(nodeInput))) {
            String nextLocation;
            while ((nextLocation = br.readLine()) != null) {
                String[] details = nextLocation.split(",");
                String buildingName = details[0];
                int buildingID = Integer.parseInt(details[1]);

                if(!buildingName.equals("")) {
                    Set<Room> rooms = new HashSet<>();
                    Building newBuilding = new Building(null, rooms, buildingName, buildingID);
                    buildings.put(buildingID, newBuilding);
                    buildingsNameToId.put(buildingName, buildingID);
                }
            }
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Invalid nodes file");
        } catch (IOException io) {
            System.out.println("IOException while opening file in addNodes");
        }
    }

    /**
     * Add the nodes from the nodeInput file to the graph
     * @param nodeInput the file from which to retrieve the nodes
     * @param floorplan the floorplan of all the nodes being added
     * @param buildingId the building that these nodes are in
     */
    private void addNodes(InputStream nodeInput, int floorplan, int buildingId) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(nodeInput))) {
            String nextLocation;
            while ((nextLocation = br.readLine()) != null) {
                String[] details = nextLocation.split(",");
                String locationName = details[0];
                int locationID = Integer.parseInt(details[1]);
                int modified_ID = (floorplan * ID_MOD) + locationID;
                double xCoord = Integer.parseInt(details[2]);
                double yCoord = Integer.parseInt(details[3]);
                if(!locationName.equals("")) {  //ignore intersections
                    Building building;
                    Room room = null;
                    Facility fac = null;
                    if(buildingId == 0) {  //This is a building on campus
                        //Add the "overall building" room
                        building = buildings.get(locationID);
                        room = new Room(modified_ID, floorplan, xCoord, yCoord,
                                locationName, building.getName());
                    }
                    else if (locationName.charAt(0) == '$') { //This is a facility on campus
                        /*String facName = locationName.substring(2,locationName.length()-1);
                        facName = String.
                        FacilityType fType = FacilityType.valueOf();*/
                        FacilityType fType = null;
                        if (locationName.equals("$[MBATHROOM]"))
                            fType = FacilityType.MBathroom;
                        else if (locationName.equals("$[WBATHROOM]"))
                            fType = FacilityType.WBathroom;
                        else if (locationName.equals("$[WATERFOUNTAIN]"))
                            fType = FacilityType.WaterFountain;
                        else if (locationName.equals("$[PRINTER]"))
                            fType = FacilityType.Printer;
                        else if (locationName.equals("$[VENDINGMACHINE]"))
                            fType = FacilityType.VendingMachine;
                        else throw new IOException("Unknown Facility Type: " + locationName);
                        building = buildings.get(locationID);
                        fac = new Facility(modified_ID,floorplan,xCoord,yCoord,fType);
                    }
                    else {
                        building = buildings.get(buildingId);
                        room = new Room(modified_ID, floorplan,
                                xCoord, yCoord, locationName,
                                building.getName());
                    }

                    if (room != null) {
                        building.add(room);
                        campusGraph.addVertex(room);
                        locations.put(modified_ID, room);
                    }
                    else if (fac != null) {
                        building.add(fac);
                        campusGraph.addVertex(fac);
                        locations.put(modified_ID, fac);
                    }
                    else throw new RuntimeException(String.format(
                            "Could not add new location with ID: &1$d and building ID: %2$d",
                                locationID,buildingId));


                }
                else {
                    Intersection intersection = new Intersection(modified_ID, floorplan, xCoord, yCoord);
                    campusGraph.addVertex(intersection);
                    locations.put(modified_ID, intersection);
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
    private void addEdges(InputStream edgeInput, int floorplan) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(edgeInput))) {
            String nextEdge;
            while ((nextEdge = br.readLine()) != null) {
                String[] details = nextEdge.split(",");
                int firstLocation = Integer.parseInt(details[0]) + (floorplan * ID_MOD);
                int secondLocation = Integer.parseInt(details[1]) + (floorplan * ID_MOD);
                DefaultWeightedEdge edge =
                        campusGraph.addEdge(locations.get(firstLocation), locations.get(secondLocation));
                campusGraph.setEdgeWeight(edge,
                        getDistance(locations.get(firstLocation), locations.get(secondLocation)));
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Invalid edge file");
        } catch (IOException io) {
            System.out.println("IOException while opening file in addEdges");
        }
    }

    /**
     * Add edges from a cross-floorplan edge file
     * @param edgeInput the file that contains cross-floorplan edges
     */
    private void addEdgesCross(InputStream edgeInput) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(edgeInput))) {
            String nextEdge;
            while ((nextEdge = br.readLine()) != null) {
                String[] details = nextEdge.split(",");
                int firstFloorplan = Integer.parseInt(details[0]);
                int firstLocation = Integer.parseInt(details[1]);
                int secondFloorplan = Integer.parseInt(details[2]);
                int secondLocation = Integer.parseInt(details[3]);
                double distance = Integer.parseInt(details[4]);

                firstLocation = firstLocation + (firstFloorplan * ID_MOD);
                secondLocation = secondLocation + (secondFloorplan * ID_MOD);

                DefaultWeightedEdge edge =
                        campusGraph.addEdge(locations.get(firstLocation), locations.get(secondLocation));
                campusGraph.setEdgeWeight(edge, distance);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Invalid edge file");
        } catch (IOException io) {
            System.out.println("IOException while opening file in addEdges");
        }
    }

    /**
     * Get the distance between two locations
     * @param l1 the first location
     * @param l2 the second location
     * @return the straight-line distance between these locations.
     */
    private double getDistance(Location l1, Location l2) {
        return Math.sqrt(Math.pow(l1.getX() - l2.getX(), 2) + Math.pow(l1.getY() - l2.getY(), 2));
    }

    /**
     * @return a map of all buildings mapped to their IDs
     */
    public Map<String, Integer> getBuildings() {
        return new HashMap<>(buildingsNameToId);
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
     *  Split a path by floorplan for frontend viewing
     * @param shortestPath the path to split
     * @return a path divided into sections by the floorplan to which
     * each Location on the path belongs to
     */
    private List<List<Location>> splitPaths(GraphPath shortestPath) {
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
            if(i < path.size()) {
                currentFloorplan = path.get(i).getFloorPlan();
            }
        }
        return segmentedPath;
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
        return splitPaths(shortestPath);
    }

    /**
     * Get the shortest path between the starting location and a facility of given type
     * @param start ID of the first location
     * @param type type of facility to find
     * @return a path representing the shortest path to the nearest 'type' facility
     */
    public List<List<Location>> getShortestPath(int start, FacilityType type) {
        //Get starting location from map
        Location startLocation = locations.get(start);
        Facility minDest = null;
        Double minDist = null;

        //Find all shortest paths from the starting location using Dijkstra's Algorithm
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(campusGraph);

        //Search all facilities of given type for nearest one

        GraphPath currentShortestPath = null;
        GraphPath facilityPath;
        double currentLowestWeight = Double.MAX_VALUE;

        for (Map.Entry<Integer,Building> entry : buildings.entrySet()) {
            Building building = entry.getValue();
            Iterator<Facility> facilityIterator = building.facilityIterator(type);
            while (facilityIterator.hasNext()) {
                Facility facility = facilityIterator.next();
                if(facility.getType() == type) {
                    facilityPath = dijkstraShortestPath.getPath(startLocation, facility);
                    if(facilityPath.getWeight() < currentLowestWeight) {
                        currentShortestPath = facilityPath;
                        currentLowestWeight = facilityPath.getWeight();
                    }
                }
            }
        }

        //Split paths by floorplan
        return splitPaths(currentShortestPath);
    }
}