package com.nullpointers.pathpointer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private List<List<Location>> path;

    public SectionsPagerAdapter(FragmentManager fm, List<List<Location>> path) {
        super(fm);
        this.path = path;
    }

    @Override
    public Fragment getItem(int position) {
        return FloorPlanFragment.newInstance((ArrayList<Location>)path.get(position));
    }

    @Override
    public int getCount() {
        return path.size();
    }
}
