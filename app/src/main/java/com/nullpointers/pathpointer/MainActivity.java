package com.nullpointers.pathpointer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceBuildingSpinner;
    private Spinner destinationBuildingSpinner;

    private static class StringWithTag implements Comparable<StringWithTag> {
        public String string;
        public Object tag;

        public StringWithTag(String string, Object tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }

        @Override
        public int compareTo(StringWithTag other) {
            return string.compareTo(other.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Campus campus = new Campus(this, "Data/Nodes", "Data/Edges");
        Map<String, Integer> buildingMap = campus.getBuildings();
        sourceBuildingSpinner = (Spinner)findViewById(R.id.spinner1);
        destinationBuildingSpinner = (Spinner)findViewById(R.id.spinner2);
        List<StringWithTag> buildingList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : buildingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            buildingList.add(new StringWithTag(key, value));
        }
        Collections.sort(buildingList);
        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceBuildingSpinner.setAdapter(buildingAdapter);
        destinationBuildingSpinner.setAdapter(buildingAdapter);
    }

    public void viewCampusActivity(View view) {
        Intent intent = new Intent(this, CampusActivity.class);
        StringWithTag selectedItem = (StringWithTag)(sourceBuildingSpinner).getSelectedItem();
        int sourceBuilding = (int)selectedItem.tag;
        intent.putExtra("sourceBuilding", sourceBuilding);
        selectedItem = (StringWithTag)(destinationBuildingSpinner).getSelectedItem();
        int destinationBuilding = (int)selectedItem.tag;
        intent.putExtra("destinationBuilding", destinationBuilding);
        startActivity(intent);
    }
}
