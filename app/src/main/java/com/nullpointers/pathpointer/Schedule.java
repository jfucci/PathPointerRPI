package com.nullpointers.pathpointer;

import java.util.Collection;
import java.util.Iterator;

/**
 * The Building class represents a Building on the Campus.  It contains references to all the Rooms
 * and Facilities in a Building, and provides functions to view but not edit these collections.
 *
 * @author Matthew R. Dobbins
 * Created on Wed, 2017-Mar-23
 */
public class Schedule {

    /** The location of the Schedule file on the device */
    public static final String FILENAME = "schedule.json";

    /**
     * Constructs a new Schedule object, which will contain events as specified in the file located
     * at FILENAME.  If such a file does not exist, it will be initialized as an empty Schedule
     */
    private Schedule() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /**
     * Saves the current schedule to the file located at FILENAME.
     * @return True if the schedule was saved successfully, false otherwise
     */
    private boolean saveToFile() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /**
     * Loads the current schedule from the file located at FILENAME.
     * @return True if the schedule was loaded successfully, false otherwise
     */
    private boolean loadFromFile() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /** Returns the single instance of Schedule */
    public static Schedule getInstance() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /** Returns the number of Events in this Schedule */
    public int size() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /** Returns true if and only if this.size() == 0 */
    public boolean isEmpty() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /**
     * Determines if the Schedule contains the specified event.  Specifically, returns true if
     * there exists some event o in the schedule such that o.equals(e) || (o == null && e == null)
     * and false otherwise.
     * @return True if the Schedule contains [e], false otherwise
     */
    public boolean contains(Event e) {
        throw new RuntimeException("Not Yet Implemented");
    }

    /** Returns an Iterator over the Events in this Schedule */
    public Iterator<Event> iterator() {
        throw new RuntimeException("Not Yet Implemented");
    }

    /**
     * Attempts to add an Event to the Schedule.  The added event must not overlap with any other
     * Events that are already contained.
     * @param event The Event to add
     * @return True if the set of events was modified by this operation, false otherwise
     */
    public boolean add(Event event) {
        throw new RuntimeException("Not Yet Implemented");
    }

    /**
     * Attempts to remove the specified Event from the Schedule.  More specifically, if the Schedule
     * contains an Event o such that o.equals(event) || (o == null && event == null), then it will
     * not contain o upon return of this function.
     * @param event The Event to remove.
     * @return True if the set of Events was modified by this operation, false otherwise
     */
    public boolean remove(Event event) {
        throw new RuntimeException("Not Yet Implemented");
    }

    /** Removes any and all Events in this Schedule */
    public void clear() {
        throw new RuntimeException("Not Yet Implemented");
    }
}
