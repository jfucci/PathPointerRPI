package com.nullpointers.pathpointer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoomChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoomChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomChooserFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

    private Spinner sourceBuildingSpinner;
    private Spinner destinationBuildingSpinner;
    private Spinner sourceRoomSpinner;
    private Spinner destinationRoomSpinner;
    Campus campus;

    public RoomChooserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RoomChooserFragment.
     */
    public static RoomChooserFragment newInstance() {
        RoomChooserFragment fragment = new RoomChooserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_room_chooser, container, false);
        final Button button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(this);

        campus = new Campus(this.getContext(), "Data/Nodes", "Data/Edges");
        Map<String, Integer> buildingMap = campus.getBuildings();
        // create two dropdowns to list buildings
        List<StringWithTag> buildingList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : buildingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            buildingList.add(new StringWithTag(key, value));
        }
        Collections.sort(buildingList);

        sourceBuildingSpinner = (Spinner) view.findViewById(R.id.spinner1);
        destinationBuildingSpinner = (Spinner) view.findViewById(R.id.spinner2);
        sourceRoomSpinner= (Spinner) view.findViewById(R.id.spinner3);
        destinationRoomSpinner = (Spinner) view.findViewById(R.id.spinner4);

        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceBuildingSpinner.setAdapter(buildingAdapter);
        destinationBuildingSpinner.setAdapter(buildingAdapter);
        sourceBuildingSpinner.setOnItemSelectedListener(this);
        destinationBuildingSpinner.setOnItemSelectedListener(this);
        return view;
    }

    public void onNothingSelected (AdapterView<?> parent) {
    }

    public void onItemSelected (AdapterView<?> parent, View view, int position, long id){
        StringWithTag sourceBuilding = (StringWithTag) (sourceBuildingSpinner).getSelectedItem();
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

        StringWithTag destinationBuilding = (StringWithTag) (destinationBuildingSpinner).getSelectedItem();
        int destinationId = (int) destinationBuilding.tag;
        Map<String, Integer> destinationRoomMap = campus.getRooms(destinationId);
        List<StringWithTag> destinationRoomList = new ArrayList<>();
        for (Map.Entry<String,Integer> entry: destinationRoomMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            destinationRoomList.add(new StringWithTag(key, value));
        }
        if(destinationRoomList.size() == 1) {
            destinationRoomList.clear();
        }
        ArrayAdapter<StringWithTag> destinationRoomAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, destinationRoomList);
        destinationRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceRoomSpinner.setAdapter(sourceRoomAdapter);
        destinationRoomSpinner.setAdapter(destinationRoomAdapter);

    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            StringWithTag selectedItem;
            if(sourceRoomSpinner.getAdapter().getCount() > 0) {
                selectedItem = (StringWithTag) sourceRoomSpinner.getSelectedItem();
            } else {
                selectedItem = (StringWithTag) sourceBuildingSpinner.getSelectedItem();
            }
            int source = (int)selectedItem.tag;
            if(destinationRoomSpinner.getAdapter().getCount() > 0) {
                selectedItem = (StringWithTag) destinationRoomSpinner.getSelectedItem();
            } else {
                selectedItem = (StringWithTag) destinationBuildingSpinner.getSelectedItem();
            }
            int destination = (int)selectedItem.tag;
            mListener.onRoomFragmentInteraction(source, destination);
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

    public interface OnFragmentInteractionListener {
        void onRoomFragmentInteraction(int source, int destination);
    }
}
