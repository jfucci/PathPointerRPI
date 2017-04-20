package com.nullpointers.pathpointer;

import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OccurenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OccurenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OccurenceFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,
        Spinner.OnItemSelectedListener,
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener {

    private Schedule schedule;
    private boolean[] daysOfWeek = {false, false, false, false, false, false, false};
    private Calendar cal;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private StringWithTag sourceBuilding;
    private StringWithTag sourceRoom;
    private Campus campus;
    private EditText editText;

    private OnFragmentInteractionListener mListener;

    public OccurenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OccurenceFragment.
     */
    public static OccurenceFragment newInstance() {
        OccurenceFragment fragment = new OccurenceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_occurence, container, false);
        schedule.getInstance();
        cal.getInstance();
        editText = (EditText) view.findViewById(R.id.editText);
        CheckBox monChk=(CheckBox)view.findViewById(R.id.checkBox1);
        CheckBox tueChk=(CheckBox)view.findViewById(R.id.checkBox2);
        CheckBox wedChk=(CheckBox)view.findViewById(R.id.checkBox3);
        CheckBox thuChk=(CheckBox)view.findViewById(R.id.checkBox4);
        CheckBox friChk=(CheckBox)view.findViewById(R.id.checkBox5);
        CheckBox satChk=(CheckBox)view.findViewById(R.id.checkBox6);
        CheckBox sunChk=(CheckBox)view.findViewById(R.id.checkBox7);
        monChk.setOnCheckedChangeListener(this);
        tueChk.setOnCheckedChangeListener(this);
        wedChk.setOnCheckedChangeListener(this);
        thuChk.setOnCheckedChangeListener(this);
        friChk.setOnCheckedChangeListener(this);
        satChk.setOnCheckedChangeListener(this);
        sunChk.setOnCheckedChangeListener(this);
        Button addButton = (Button)view.findViewById(R.id.add);
        campus = new Campus(this.getContext(), "Data/Nodes", "Data/Edges");
        Map<String, Integer> buildingMap = campus.getBuildings();
        Spinner buildingSpinner = (Spinner)view.findViewById(R.id.bSpinner);
        List<StringWithTag> buildingList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : buildingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            buildingList.add(new StringWithTag(key, value));
        }
        Collections.sort(buildingList);
        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingAdapter);
        buildingSpinner.setOnItemSelectedListener(this);
        addButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.add:
                Spinner roomSpinner = (Spinner)view.findViewById(R.id.spinner3);
                sourceRoom = (StringWithTag) (roomSpinner).getSelectedItem();
                Time startTime = new Time(startHour,startMinute);
                Time endTime = new Time(endHour, endMinute);
                List<Room> roomList = campus.getRoomList((int)sourceBuilding.tag);
                Room location = new Room();
                for (int i = 0; i < roomList.size(); i++){
                    if (roomList.get(i).getName() == sourceRoom.string){
                        location = roomList.get(i);
                    }
                }
                Occurrence occur = new Occurrence(daysOfWeek,startTime,endTime, location);
                Set<Occurrence> occurrenceSet = new HashSet<>();
                occurrenceSet.add(occur);
                String eventName = editText.getText().toString();
                Event newEvent = new Event(eventName,occurrenceSet);
                schedule.add(newEvent);
        }
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id){
        Spinner roomSpinner = (Spinner)view.findViewById(R.id.spinner3);
        Spinner buildingSpinner = (Spinner)view.findViewById(R.id.bSpinner);
        StringWithTag sourceBuilding = (StringWithTag) buildingSpinner.getSelectedItem();
        int sourceId = (int)sourceBuilding.tag;
        Map<String, Integer> sourceRoomMap = campus.getRooms(sourceId);
        List<StringWithTag> sourceRoomList = new ArrayList<>();
        for (Map.Entry<String,Integer> entry: sourceRoomMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            sourceRoomList.add(new StringWithTag(key, value));
        }
        Collections.sort(sourceRoomList);

        if(sourceRoomList.size() == 1) {
            sourceRoomList.clear();
        }

        ArrayAdapter<StringWithTag> sourceRoomAdapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_spinner_item, sourceRoomList);
        sourceRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roomSpinner.setAdapter(sourceRoomAdapter);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.checkBox1:
                if(isChecked == true) {
                    daysOfWeek[0] = true;
                } else{
                    daysOfWeek[0] = false;
                }
            case R.id.checkBox2:
                if(isChecked == true) {
                    daysOfWeek[1] = true;
                } else{
                    daysOfWeek[1] = false;
                }
            case R.id.checkBox3:
                if(isChecked == true) {
                    daysOfWeek[2] = true;
                } else{
                    daysOfWeek[2] = false;
                }
            case R.id.checkBox4:
                if(isChecked == true) {
                    daysOfWeek[3] = true;
                } else{
                    daysOfWeek[3] = false;
                }
            case R.id.checkBox5:
                if(isChecked == true) {
                    daysOfWeek[4] = true;
                } else{
                    daysOfWeek[4] = false;
                }
            case R.id.checkBox6:
                if(isChecked == true) {
                    daysOfWeek[5] = true;
                } else{
                    daysOfWeek[5] = false;
                }
            case R.id.checkBox7:
                if(isChecked == true) {
                    daysOfWeek[5] = true;
                } else{
                    daysOfWeek[5] = false;
                }
        }
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onOccurenceFragmentInteraction();
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
        void onOccurenceFragmentInteraction();
    }
}
