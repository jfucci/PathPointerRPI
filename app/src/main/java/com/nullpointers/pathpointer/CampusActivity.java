package com.nullpointers.pathpointer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ortiz.touch.TouchImageView;

import java.util.List;

public class CampusActivity extends AppCompatActivity {

    TouchImageView campusView;
    private static final int originalImageWidth = 2175;
    private static final int originalImageHeight = 2055;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);
        Intent intent = getIntent();

        Campus campus = new Campus(this, "Data/Nodes", "Data/Edges");
        int sourceBuilding = intent.getIntExtra("sourceBuilding", 1);
        int destinationBuilding = intent.getIntExtra("destinationBuilding", 2);

        List<List<Location>> path = campus.getShortestPath(sourceBuilding, destinationBuilding);
        List<Location> campusPath = path.get(0);


        campusView = (TouchImageView)findViewById(R.id.campusView);
        Resources res = getResources();
        Bitmap imgBitmap = BitmapFactory.decodeResource(res, R.drawable.campusmap);
        Bitmap cBitmap = imgBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(cBitmap);
        campusView.setImageBitmap(cBitmap);

        for (int i = 1; i < campusPath.size(); i++) {
            drawPath(canvas, Color.BLUE, campusPath.get(i - 1).getX(), campusPath.get(i - 1).getY(), campusPath.get(i).getX(), campusPath.get(i).getY());
        }
    }

    private void drawPath(Canvas canvas, int color, double startx, double starty, double endx, double endy) {
        double viewWidth = campusView.getDrawable().getIntrinsicWidth();
        double viewHeight = campusView.getDrawable().getIntrinsicHeight();
        double xscale = viewWidth / originalImageWidth;
        double yscale = viewHeight / originalImageHeight;
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawLine((float)(startx*xscale), (float)(starty*yscale), (float)(endx*xscale), (float)(endy*yscale), paint);
    }
}
