package com.nullpointers.pathpointer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * In immutable class that represents a single weekly event in the schedule,
 * with one or more occurrences.
 */

public class Event {
    //The name of this event
    private final String name;

    //The weekly occurrences of this event
    private final Set<Occurrence> occurrences;

    /**
     * Initialize a new Event with the given parameters. The occurrence set will be copied to avoid
     * representation exposure. Ensures that none of the occurrences overlap with each other.
     * @param name the name of the event
     * @param occurrences the set of occurrences this event has
     * @throws IllegalArgumentException if occurrences contains overlapping occurrences
     */
    public Event(String name, Set<Occurrence> occurrences) {
        this.name = name;
        Set<Occurrence> newOccurrences = new HashSet<>();
        for(Occurrence occurrence : occurrences) {
            for(Occurrence newOccurrence : newOccurrences) {
                if(newOccurrence.overlaps(occurrence)) {
                    throw new IllegalArgumentException("Occurrences cannot overlap in event");
                }
            }
            newOccurrences.add(occurrence);
        }
        this.occurrences = newOccurrences;
    }

    /**
     * @return the name of this string
     */
    public String getName() {
        return name;
    }

    /**
     * @return an iterator for the event's set of occurrences
     */
    public Iterator<Occurrence> occurIterator() {
        return occurrences.iterator();
    }

    /**
     * @return the number of occurrences that this event has
     */
    public int numberOfOccurrences() {
        return occurrences.size();
    }

    /**
     * Check if this event has a certain occurrence
     * @param o the occurrence to check for in this event
     * @return true if o is in this event, false otherwise
     */
    public boolean containsOccurrence(Occurrence o) {
        return occurrences.contains(o);
    }

    /**
     * Determine if this event has any overlapping occurrences with event e
     * @param e the event to compare this event to
     * @return true if this event overlaps e, false otherwise
     */
    public boolean overlaps(Event e) {
        Iterator<Occurrence> eItr = e.occurIterator();
        while(eItr.hasNext()) {
            Occurrence eOccurrence = eItr.next();
            for (Occurrence occurrence : occurrences) {
                if(eOccurrence.overlaps(occurrence)) {
                    return true;
                }
            }
        }
        return false;
    }
}
