package com.nullpointers.pathpointer;

import android.content.Context;
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
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuildingChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuildingChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildingChooserFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private Spinner sourceBuildingSpinner;
    private Spinner destinationBuildingSpinner;

    public BuildingChooserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuildingChooserFragment.
     */

    public static BuildingChooserFragment newInstance() {
        BuildingChooserFragment fragment = new BuildingChooserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_building_chooser, container, false);

        Campus campus = new Campus(this.getContext(), "Data/Nodes", "Data/Edges");
        Map<String, Integer> buildingMap = campus.getBuildings();

        List<StringWithTag> buildingList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : buildingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            buildingList.add(new StringWithTag(key, value));
        }
        Collections.sort(buildingList);

        sourceBuildingSpinner = (Spinner) view.findViewById(R.id.spinner1);
        destinationBuildingSpinner = (Spinner) view.findViewById(R.id.spinner2);

        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceBuildingSpinner.setAdapter(buildingAdapter);
        destinationBuildingSpinner.setAdapter(buildingAdapter);
        final Button button = (Button) view.findViewById(R.id.button);

        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            StringWithTag selectedItem = (StringWithTag)(sourceBuildingSpinner).getSelectedItem();
            int sourceBuilding = (int)selectedItem.tag;
            selectedItem = (StringWithTag)(destinationBuildingSpinner).getSelectedItem();
            int destinationBuilding = (int)selectedItem.tag;
            mListener.onBuildingFragmentInteraction(sourceBuilding, destinationBuilding);
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
        void onBuildingFragmentInteraction(int sourceBuilding, int destinationBuilding);
    }
}
