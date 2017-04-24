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
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FacilityChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FacilityChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacilityChooserFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    private OnFragmentInteractionListener mListener;

    private Spinner sourceBuildingSpinner;
    private Spinner sourceRoomSpinner;
    private Campus campus;

    public FacilityChooserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FacilityChooserFragment.
     */
    public static FacilityChooserFragment newInstance() {
        return new FacilityChooserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facility_chooser, container, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        // display the facilities available for navigation
        for (FacilityType facilityType : FacilityType.values()) {
            if(facilityType != FacilityType.Bathroom) {
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.buttonlayout);
                Button btn = new Button(this.getContext());
                btn.setId(facilityType.getValue());
                switch(facilityType) {
                    case MBathroom:
                        btn.setText(R.string.mbathroom);
                        break;
                    case WBathroom:
                        btn.setText(R.string.wbathroom);
                        break;
                    case WaterFountain:
                        btn.setText(R.string.waterfountain);
                        break;
                    case Printer:
                        btn.setText(R.string.printer);
                        break;
                    case VendingMachine:
                        btn.setText(R.string.vendingmachine);
                        break;
                    default:
                        break;
                }
                final int id_ = btn.getId();
                layout.addView(btn, params);
                Button btn1 = ((Button) view.findViewById(id_));
                btn1.setOnClickListener(this);
            }
        }

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
        sourceRoomSpinner= (Spinner) view.findViewById(R.id.spinner2);

        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceBuildingSpinner.setAdapter(buildingAdapter);
        sourceBuildingSpinner.setOnItemSelectedListener(this);
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

        sourceRoomSpinner.setAdapter(sourceRoomAdapter);

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
            mListener.onFacilityFragmentInteraction(source, FacilityType.values()[view.getId()]);
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
        void onFacilityFragmentInteraction(int sourceRoom, FacilityType facilityType);
    }
}
