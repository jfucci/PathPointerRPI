package com.nullpointers.pathpointer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ortiz.touch.TouchImageView;

public class CampusActivity extends AppCompatActivity {

    TouchImageView campusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);

        campusView = (TouchImageView)findViewById(R.id.campusView);
        Resources res = getResources();
        Bitmap imgBitmap = BitmapFactory.decodeResource(res, R.drawable.campusmap);

        Bitmap cBitmap = imgBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(cBitmap);

        // Line
        drawLine(canvas, Color.GREEN, 900, 1000, 900, 800);
        drawLine(canvas, Color.GREEN, 900, 800, 1600, 800);

        campusView.setImageBitmap(cBitmap);
    }

    private void drawLine(Canvas canvas, int color, int startx, int starty, int endx, int endy) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
        canvas.drawLine(startx, starty, endx, endy, paint);
    }
}
