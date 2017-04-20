package com.nullpointers.pathpointer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RoomChooserFragment.OnFragmentInteractionListener,
        BuildingChooserFragment.OnFragmentInteractionListener,
        FacilityChooserFragment.OnFragmentInteractionListener,
        ScheduleUi.OnFragmentInteractionListener,
        OccurenceFragment.OnFragmentInteractionListener,
        DeleteEventFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass;

        if (id == R.id.nav_building_building) {
            fragmentClass = BuildingChooserFragment.class;
        } else if (id == R.id.nav_room_room) {
            fragmentClass = RoomChooserFragment.class;
        } else if (id == R.id.nav_facility) {
            fragmentClass = FacilityChooserFragment.class;
        } else {
            fragmentClass = ScheduleUi.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContent, fragment).commit();
        fragmentTransaction.addToBackStack(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRoomFragmentInteraction(int source, int destination) {
        Fragment fragment = null;

        try {
            fragment = FloorPlanViewGroupFragment.newInstance(source, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContent, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onBuildingFragmentInteraction(int sourceBuilding, int destinationBuilding) {
        onRoomFragmentInteraction(sourceBuilding, destinationBuilding);
    }

    @Override
    public void onFacilityFragmentInteraction(int sourceRoom, FacilityType facilityType) {
        Fragment fragment = null;

        try {
            fragment = FloorPlanViewGroupFragment.newInstance(sourceRoom, facilityType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContent, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onScheduleFragmentInteraction(int event) {
        Fragment fragment = null;

        try {
            switch(event) {
                case 1:
                    fragment = ScheduleActivity.newInstance();
                    break;
                case 2:
                    fragment = OccurenceFragment.newInstance();
                    break;
                case 3:
                    fragment = DeleteEventFragment.newInstance();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.flContent, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onOccurenceFragmentInteraction() {

    }

    @Override
    public void onDeleteFragmentInteraction() {
    }
}