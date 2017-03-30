package com.nullpointers.pathpointer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static class StringWithTag {
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
    }

    Campus campus;
    Map<String, Integer> buildingMap;
    Spinner sourceBuildingSpinner;
    Spinner destinationBuildingSpinner;
    ArrayAdapter<CharSequence> buildingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        campus = new Campus(this, "Data/Nodes", "Data/Edges");
        buildingMap = new HashMap<String, Integer>();
        buildingMap = campus.getBuildings();
        sourceBuildingSpinner = (Spinner)findViewById(R.id.spinner1);
        destinationBuildingSpinner = (Spinner)findViewById(R.id.spinner2);
        List<StringWithTag> buildingList = new ArrayList<StringWithTag>();
        for(Map.Entry<String,Integer> entry : buildingMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            buildingList.add(new StringWithTag(key, value));
        }
        ArrayAdapter<StringWithTag> buildingAdapter = new ArrayAdapter<StringWithTag>(this, android.R.layout.simple_spinner_item, buildingList);
        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceBuildingSpinner.setAdapter(buildingAdapter);
        destinationBuildingSpinner.setAdapter(buildingAdapter);

        sourceBuildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag sourceBuilding = (StringWithTag) parent.getItemAtPosition(position);
                Integer sourceKey = (Integer) sourceBuilding.tag;
                Toast.makeText(getBaseContext(), sourceKey + " selected.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        destinationBuildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag destinationBuilding = (StringWithTag) parent.getItemAtPosition(position);
                Integer destinationKey = (Integer) destinationBuilding.tag;
                Toast.makeText(getBaseContext(), destinationKey + " selected.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void viewCampusActivity(View view) {
        Intent intent = new Intent(this, CampusActivity.class);
        startActivity(intent);
    }
}
