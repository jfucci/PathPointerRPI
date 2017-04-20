package com.nullpointers.pathpointer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ortiz.touch.TouchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FloorPlanFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String PATH = "path";

    private TouchImageView mapView;

    private static final int campusImageWidth = 2175;
    private static final int campusImageHeight = 2055;
    private static final int otherImageWidth = 2200;
    private static final int otherImageHeight = 1700;

    public FloorPlanFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FloorPlanFragment newInstance(ArrayList<Location> path) {
        FloorPlanFragment fragment = new FloorPlanFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_floor_plan, container, false);
        List<Location> path = getArguments().getParcelableArrayList(PATH);
        int floorPlanRes = this.getContext().getResources().getIdentifier("map_" + path.get(0).getFloorPlan(), "drawable", this.getContext().getPackageName());

        mapView = (TouchImageView) view.findViewById(R.id.mapView);
        Resources res = getResources();
        Bitmap imgBitmap = BitmapFactory.decodeResource(res, floorPlanRes);
        Bitmap cBitmap = imgBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(cBitmap);
        mapView.setImageBitmap(cBitmap);

        for (int i = 1; i < path.size(); i++) {
            drawPath(canvas, path.get(0).getFloorPlan(), Color.BLUE, path.get(i - 1).getX(), path.get(i - 1).getY(), path.get(i).getX(), path.get(i).getY());
        }
        drawDot(canvas, path.get(0).getFloorPlan(), Color.GREEN, path.get(0).getX(), path.get(0).getY());

        return view;
    }

    private double getXScale(double viewWidth, int floorplan) {
        if(floorplan == 0) {
            return viewWidth / campusImageWidth;
        }
        return viewWidth / otherImageWidth;
    }

    private double getYScale(double viewHeight, int floorplan) {
        if(floorplan == 0) {
            return viewHeight / campusImageHeight;
        }
        return viewHeight / otherImageHeight;
    }

    private void drawPath(Canvas canvas, int floorPlan, int color, double startx, double starty, double endx, double endy) {
        double xscale = getXScale(mapView.getDrawable().getIntrinsicWidth(), floorPlan);
        double yscale = getYScale(mapView.getDrawable().getIntrinsicHeight(), floorPlan);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawLine((float)(startx*xscale), (float)(starty*yscale), (float)(endx*xscale), (float)(endy*yscale), paint);
    }

    private void drawDot(Canvas canvas, int floorPlan, int color, double x, double y) {
        double xscale = getXScale(mapView.getDrawable().getIntrinsicWidth(), floorPlan);
        double yscale = getYScale(mapView.getDrawable().getIntrinsicHeight(), floorPlan);

        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle((float)(x*xscale), (float)(y*yscale), 12, paint);
    }
}
