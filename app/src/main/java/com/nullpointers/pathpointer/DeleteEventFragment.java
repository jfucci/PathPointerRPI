package com.nullpointers.pathpointer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeleteEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeleteEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteEventFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Spinner eventSpinner;
    private Schedule sched;

    private OnFragmentInteractionListener mListener;

    private static class EventWithTag implements Comparable<EventWithTag> {
        public String string;
        public Event tag;

        public EventWithTag(String string, Event tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }


        @Override
        public int compareTo(EventWithTag other) {
            return string.compareTo(other.toString());
        }
    }

    public DeleteEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DeleteEventFragment.
     */
    public static DeleteEventFragment newInstance() {
        DeleteEventFragment fragment = new DeleteEventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_event, container, false);
        sched = Schedule.getInstance();
        Button deleteEvent = (Button)view.findViewById(R.id.button2);
        deleteEvent.setOnClickListener(this);
        eventSpinner = (Spinner)view.findViewById(R.id.spinner);
        List<EventWithTag> eventList = new ArrayList<>();
        Iterator<Event> eventIterator = sched.iterator();
        while(eventIterator.hasNext()){
            String key = eventIterator.next().getName();
            Event event = eventIterator.next();

            eventList.add(new EventWithTag(key,event));
        }
        ArrayAdapter<EventWithTag> eventAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, eventList);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);
        return view;
    }

    @Override
    public void onClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to delete this event?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventWithTag selectedItem = (EventWithTag) eventSpinner.getSelectedItem();
                        sched.remove(selectedItem.tag);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete Event");
        alert.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onDeleteFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDeleteFragmentInteraction();
    }
}