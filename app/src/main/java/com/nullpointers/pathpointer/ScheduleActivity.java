package com.nullpointers.pathpointer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;

import java.util.Iterator;


public class ScheduleActivity extends Fragment {

    private static Schedule sched;

    private static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public ScheduleActivity() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScheduleFragment.
     */
    public static ScheduleActivity newInstance() {
        ScheduleActivity fragment = new ScheduleActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule, container, false);
        Calendar calendar = Calendar.getInstance();
        int focusHours = calendar.get(Calendar.HOUR_OF_DAY);
        int focusMinutes = calendar.get(Calendar.MINUTE);
        int focus = (60*focusHours) + focusMinutes;
        LinearLayout.LayoutParams focusParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(1));
        focusParams.setMargins(0,dpToPx(focus), 0, 0);
        view.findViewById(R.id.currentTimeLineView).setLayoutParams(focusParams);
        sched = sched.getInstance();
        // Start loop to iterate through every occurrence of any event and populate schedule UI
        if (!sched.isEmpty()){
            Iterator<Event> schedIterator = sched.iterator();
            while (schedIterator.hasNext()){
                Event currEvent = schedIterator.next();
                while (currEvent.occurIterator().hasNext()) {
                    // need a new Textview for every occurrence of the event
                    TextView newEvent = new TextView(this.getContext());
                    Occurrence currOccur = currEvent.occurIterator().next();
                    // the block will have multiple lines of text
                    newEvent.setSingleLine(false);
                    // time to calculate what time the block will start on the schedule
                    Time start = currOccur.getStart();
                    int hours = start.getHour();
                    int minutes = start.getMinute();
                    int margin_top = (hours * 60) + minutes;
                    Time end = currOccur.getEnd();
                    // the block will have a Name, Building, Room, Start-End time
                    newEvent.setText(currEvent.getName() + "\n" + currOccur.getLocation().getBuilding() + "\n" +
                            currOccur.getLocation().getName() + "\n" + start + "-" + end);
                    int height = (end.getHour()*60 + end.getMinute()) - margin_top;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(height));
                    params.setMargins(0,dpToPx(margin_top), 0, 0);
                    newEvent.setLayoutParams(params);
                    boolean[] daysOfWeek = currOccur.getDaysOfWeek();
                    newEvent.setTextColor(Color.WHITE);
                    newEvent.setBackgroundColor(Color.BLUE);
                    // find what days of the schedule the block should be implemented
                    if (daysOfWeek[0]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.mondayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[1]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.tuesdayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[2]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.wednesdayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[3]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.thursdayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[4]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.fridayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[5]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.saturdayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                    if (daysOfWeek[6]){
                        RelativeLayout dayLayout = (RelativeLayout) view.findViewById(R.id.sundayRelativeLayout);
                        dayLayout.addView(newEvent);
                    }
                }
            }
        }
        return view;
    }
}
