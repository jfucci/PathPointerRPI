package com.nullpointers.pathpointer;

import java.util.Arrays;

/**
 * Represents an occurrence of an event.  An occurrence is a
 * subset of the days of the week with a start and end time at
 * which the event occurs. Times are based on 24 hour system.
 */
public class Occurrence {
    private boolean[] daysOfWeek;
    private Time start;
    private Time end;

    /**
     * Constructor for occurrence.
     * @param daysOfWeek the days of the week of this occurrence.  Monday is the first day
     * of the week.
     * @param start the starting time of this occurrence
     * @param end the ending time of this occurrence
     * @throws IllegalArgumentException if days of the week is not of
     * length 7.
     */
    public Occurrence(boolean[] daysOfWeek, Time start, Time end) {
        if (daysOfWeek.length != 7) {
            String message = String.format(
                    "daysofWeek needs to have length 7, not %1$d.",daysOfWeek.length);
            throw new IllegalArgumentException(message);
        }

        this.daysOfWeek = daysOfWeek;
        this.start = start;
        this.end = end;
    }

    /** Returns the days of the week during which this occurrence takes place */
    public boolean[] getDaysOfWeek() {return daysOfWeek;}

    /** Returns the starting time of this occurrence */
    public Time getStart() {return start;}

    /** Returns the ending time of this occurrence */
    public Time getEnd() {return end;}

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Occurrence)) return false;
        else {
            Occurrence o = (Occurrence) other;
            return Arrays.equals(daysOfWeek,o.getDaysOfWeek()) &&
                    start.equals(o.getStart()) && end.equals(o.getEnd());
        }
    }

    @Override
    public int hashCode() {
        return 100000*Arrays.hashCode(daysOfWeek) + 1000*start.hashCode() + end.hashCode();
    }
}
