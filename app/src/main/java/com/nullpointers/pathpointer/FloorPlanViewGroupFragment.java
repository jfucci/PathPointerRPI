package com.nullpointers.pathpointer;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FloorPlanViewGroupFragment extends Fragment {
    private static final String SOURCE_ROOM = "sourceRoom";
    private static final String DEST_ROOM = "destinationRoom";
    private static final String FACILITY = "facilityType";

    private int sourceRoom;
    private int destinationRoom;
    private FacilityType facilityType;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FloorPlanViewGroupFragment.
     */
    public static FloorPlanViewGroupFragment newInstance(int param1, int param2) {
        FloorPlanViewGroupFragment fragment = new FloorPlanViewGroupFragment();
        Bundle args = new Bundle();
        args.putInt(SOURCE_ROOM, param1);
        args.putInt(DEST_ROOM, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FloorPlanViewGroupFragment newInstance(int param1, FacilityType param2) {
        FloorPlanViewGroupFragment fragment = new FloorPlanViewGroupFragment();
        Bundle args = new Bundle();
        args.putInt(SOURCE_ROOM, param1);
        args.putSerializable(FACILITY, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sourceRoom = getArguments().getInt(SOURCE_ROOM);
            destinationRoom = getArguments().getInt(DEST_ROOM);
            facilityType = (FacilityType)getArguments().getSerializable(FACILITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floor_plan_view_group, container, false);

        Campus campus = new Campus(this.getContext(), "Data/Nodes", "Data/Edges");

        List<List<Location>> path;
        if(facilityType == null) {
            // get the nodes for the path to display from campus
            path = campus.getShortestPath(sourceRoom, destinationRoom);
        } else {
            path = campus.getShortestPath(sourceRoom, facilityType);
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), path);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return view;
    }
}
