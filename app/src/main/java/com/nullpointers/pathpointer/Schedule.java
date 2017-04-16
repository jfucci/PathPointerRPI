package com.nullpointers.pathpointer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The Schedule class represent's a user's weekly schedule.  It contains a set of non-overlapping
 * Events and can store/load these events from a file.
 *
 * @author Matthew R. Dobbins
 * Created on Wed, 2017-Apr-16
 */
public class Schedule {

    /** The location of the Schedule file on the device */
    public static final String FILENAME = "schedule.json";

    // The singleton instance
    private static Schedule instance = null;

    // Event data
    private Set<Event> events;

    /**
     * Construct a new, blank schedule
     */
    private Schedule() {
        events = new HashSet<>();
    }

    /**
     * Saves the current schedule to the file located at FILENAME.
     * Requires instance != null.
     * @return True if the schedule was saved successfully, false otherwise
     */
    private static boolean saveToFile() {
        try {
            // Store in the specified file
            File jsonFile = new File(FILENAME);

            // Store the file using JSON
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            om.writeValue(jsonFile, instance);
        } catch (IOException e) {
            return false;
        }
        
        // Done
        return true;
    }

    /**
     * Loads the current schedule from the file located at FILENAME.
     * @return True if the schedule was loaded successfully, false otherwise
     */
    private static boolean loadFromFile() {
        try {
            // Load from the specified file
            File jsonFile = new File(FILENAME);

            // Store the file using JSON
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            instance = om.readValue(jsonFile, Schedule.class);
        } catch (IOException e) {
            return false;
        }

        // Done
        return true;
    }

    /**
     * Returns the single instance of Schedule.  If an instance does not yet exist, it will be
     * loaded from the file at FILENAME.  If this file does not exist, a file equivalent to an
     * empty Schedule will be created and loaded.  If some other failure occurs, an IOException
     * will be thrown.
     * @return The instance of Schedule
     * @throws IOException if file cannot be loaded and parsed
     */
    public static Schedule getInstance() {
        if (instance == null) {
            if (!loadFromFile()) {
                instance = new Schedule();
            }
        }

        return instance;
    }

    /**
     * Determines if the Schedule contains the specified event.  Specifically, returns true if
     * there exists some event o in the schedule such that o.equals(e) || (o == null && e == null)
     * and false otherwise.
     * @return True if the Schedule contains [e], false otherwise
     */
    public boolean contains(Event e) {
        return events.contains(e);
    }

    /** Returns the number of Events in this Schedule */
    public int size() {
        return events.size();
    }

    /** Returns true if and only if this.size() == 0 */
    @JsonIgnore
    public boolean isEmpty() {
        return events.isEmpty();
    }

    /** Removes any and all Events in this Schedule */
    public void clear() {
        events.clear();
        saveToFile();
    }

    /** Returns an Iterator over the Events in this Schedule */
    public Iterator<Event> iterator() {
        return Collections.unmodifiableSet(events).iterator();
    }

    /**
     * Attempts to add an Event to the Schedule.  The added event must not overlap with any other
     * Events that are already contained.
     * @param event The Event to add
     * @return True if the set of events was modified by this operation, false otherwise
     */
    public boolean add(Event event) {
        for (Event e : events) {
            if (event.overlaps(e)) {
                return false;
            }
        }

        boolean result = events.add(event);
        saveToFile();
        return result;
    }

    /**
     * Attempts to remove the specified Event from the Schedule.  More specifically, if the Schedule
     * contains an Event o such that o.equals(event) || (o == null && event == null), then it will
     * not contain o upon return of this function.
     * @param event The Event to remove.
     * @return True if the set of Events was modified by this operation, false otherwise
     */
    public boolean remove(Event event) {
        boolean result = events.remove(event);
        saveToFile();
        return result;
    }
}
