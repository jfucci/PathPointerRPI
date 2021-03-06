package com.nullpointers.pathpointer;

import java.util.Arrays;

/**
 * Represents an occurrence of an event.  An occurrence is a
 * subset of the days of the week with a start and end time at
 * which the event occurs. Times are based on 24 hour system.
 */
public class Occurrence {
    private final boolean[] daysOfWeek;
    private final Time start;
    private final Time end;
    private final Room location;

    /** Returns an occurrence that never happens */
    private Occurrence() {
        daysOfWeek = new boolean[]{false, false, false, false, false, false, false};
        start = new Time(0, 0);
        end = new Time(0, 1);
        location = new Room();
    }

    /**
     * Constructor for occurrence.
     * @param daysOfWeek the days of the week of this occurrence.  Monday is the first day
     * of the week.
     * @param start the starting time of this occurrence
     * @param end the ending time of this occurrence
     * @throws IllegalArgumentException if days of the week is not of
     * length 7.
     */
    public Occurrence(boolean[] daysOfWeek, Time start, Time end, Room location) {
        if (daysOfWeek.length != 7) {
            String message = String.format(
                    "daysofWeek needs to have length 7, not %1$d.",daysOfWeek.length);
            throw new IllegalArgumentException(message);
        }

        this.daysOfWeek = daysOfWeek;
        this.start = start;
        this.end = end;
        this.location = location;
    }

    /** Returns the days of the week during which this occurrence takes place */
    public boolean[] getDaysOfWeek() {return daysOfWeek;}

    /** Returns the starting time of this occurrence */
    public Time getStart() {return start;}

    /** Returns the ending time of this occurrence */
    public Time getEnd() {return end;}

    /** Returns the location of this occurrence */
    public Room getLocation() {return location;}

    /**
     * Determines whether this occurrence overlaps occurrence o in time
     * @param o the occurrence to compare to
     * @return true if this occurrence overlaps o, false otherwise
     */
    public boolean overlaps(Occurrence o) {
        for(int day = 0; day < 7; day++) {
            if(this.daysOfWeek[day] && o.daysOfWeek[day]) {
                if(this.start.isEarlierThan(o.getEnd()) && this.start.isLaterThan(o.getStart())) {
                    return true;
                }
                if(o.getStart().isEarlierThan(this.end) && o.getStart().isLaterThan(this.start)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Occurrence)) return false;
        else {
            Occurrence o = (Occurrence) other;
            return Arrays.equals(daysOfWeek,o.getDaysOfWeek()) &&
                    start.equals(o.getStart()) && end.equals(o.getEnd()) &&
                    location.equals(o.getLocation());
        }
    }

    @Override
    public int hashCode() {
        return 1000000*Arrays.hashCode(daysOfWeek) + 10000*start.hashCode() + 100*end.hashCode() +
                location.hashCode();
    }
}
