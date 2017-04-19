package com.nullpointers.pathpointer;

/**
 * An immutable class to represent a time.
 * A time is made of hours and minutes.
 * 24 hour system.
 */
class Time {
    private final int hour;
    private final int minute;

    /** Returns a time corresponding to midnight */
    private Time() {
        hour = 0;
        minute = 0;
    }

    /**
     * Constructor for a time object.
     * @param hour the hour represented
     * @param minute the minute represented
     * @throws IllegalArgumentException if hour is not between 0 and 23
     * or minute is not between 0 and 60.
     */
    public Time(int hour, int minute) {
        if (0 > hour || 23 < hour || 0 > minute || 59 < minute) {
            String timeString = String.format("%1$02d:%2$02d",hour,minute);
            throw new IllegalArgumentException("Illegal time " + timeString);
        }
        this.hour = hour;
        this.minute = minute;
    }

    /** Returns the hour */
    public int getHour() {return hour;}

    /** Returns the minute */
    public int getMinute() {return minute;}

    /**
     * Returns true if this time is the same or later than t, false otherwise
     * @param t the time to compare this time to
     */
    public boolean isLaterThan(Time t) {
        if(hour == t.getHour()) {
            return minute >= t.getMinute();
        }
        return hour >= t.getHour();
    }

    /**
     * Returns true if this time is earlier than t, false otherwise
     * @param t the time to compare this time to
     */
    public boolean isEarlierThan(Time t) {
        if(hour == t.getHour()) {
            return minute < t.getMinute();
        }
        return hour < t.getHour();
    }

    @Override
    public String toString() {
        return String.format("%1$02d:%2$02d",getHour(),getMinute());
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof  Time)) return false;
        else {
            Time o = (Time) other;
            return hour == o.getHour() && minute == o.getMinute();
        }
    }

    @Override
    public int hashCode() {
        Integer h = Integer.valueOf(hour).hashCode();
        Integer m = Integer.valueOf(minute).hashCode();
        return 24*h + m;
    }
}
