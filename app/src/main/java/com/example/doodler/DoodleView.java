package com.example.doodler;

import static com.example.doodler.MainActivity.paint_brush;
import static com.example.doodler.MainActivity.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoodleView extends View {
    public ViewGroup.LayoutParams params;
    public static ArrayList<Path> pathList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();
    public static ArrayList<Integer> alphaList = new ArrayList<>();
    public static ArrayList<Float> widthList = new ArrayList<>();
    public static ArrayList<Path> undonePaths = new ArrayList<>();
    public static int current_brush_color = Color.BLACK;
    public static float current_width = 20f;
    public static int paintAlpha = 255;

    public Canvas canvas;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private Bitmap im;
    //private Bitmap canvasBitmap;


    public DoodleView(Context context) {
        super(context);
        init(context);

    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        /*paint_brush.setAntiAlias(true);
        paint_brush.setColor(Color.BLACK);
        paint_brush.setStyle(Paint.Style.STROKE);
        paint_brush.setStrokeCap(Paint.Cap.ROUND);
        paint_brush.setStrokeJoin(Paint.Join.ROUND);
        paint_brush.setStrokeWidth(50f);
        paint_brush.setAlpha(255);*/
        //pathList.add(path);
        //path = new Path();

        //params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        paint_brush = new Paint();
        paint_brush.setAntiAlias(true);
        paint_brush.setDither(true);
        paint_brush.setColor(0xFFFFFFFF);
        paint_brush.setStyle(Paint.Style.STROKE);
        paint_brush.setStrokeJoin(Paint.Join.ROUND);
        paint_brush.setStrokeCap(Paint.Cap.ROUND);
        paint_brush.setStrokeWidth(6);
        canvas = new Canvas();
        path = new Path();

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public void undo(){
        if (pathList.size() != 0) {
            undonePaths.add(pathList.remove(pathList.size()-1));
            //path.reset();
            //path = new Path();
            invalidate();
        }
    }

    public void redo(){
        if(undonePaths.size() != 0){
            pathList.add(undonePaths.remove(undonePaths.size()-1));
            invalidate();
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path = new Path();
                path.lineTo(pointX, pointY);
                pathList.add(path);
                colorList.add(current_brush_color);
                widthList.add(current_width);
                alphaList.add(paintAlpha);
                //invalidate();
                break;
        }
        return true;

    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    private void touch_start(float x, float y) {
        undonePaths.clear();
        path.reset();
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_up() {
        path.lineTo(mX, mY);
        // commit the path to our offscreen
        canvas.drawPath(path, paint_brush);
        // kill this so we don't double draw
        pathList.add(path);
        colorList.add(current_brush_color);
        widthList.add(current_width);
        alphaList.add(paintAlpha);
        //pathList.add(path);
        path = new Path();

    }

    private void touch_move(float x, float y) {
        //path = new Path();
        /*pathList.add(path);
        colorList.add(current_brush_color);
        widthList.add(current_width);
        alphaList.add(paintAlpha);*/
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }




    @Override
    protected void onDraw(Canvas canvas){
        for(int i =0; i<pathList.size(); i++){
            paint_brush.setColor(colorList.get(i));
            paint_brush.setAlpha(alphaList.get(i));
            paint_brush.setStrokeWidth(widthList.get(i));
            canvas.drawPath(pathList.get(i), paint_brush);
            invalidate();
        }
    }

}
