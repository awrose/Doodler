package com.example.doodler;

import static com.example.doodler.MainActivity.paint_brush;
import static com.example.doodler.MainActivity.path;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoodleView extends View {
    public ViewGroup.LayoutParams params;
    public static ArrayList<Path> pathList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();
    public static ArrayList<Integer> alphaList = new ArrayList<>();
    public static ArrayList<Float> widthList = new ArrayList<>();
    public static int current_brush_color = Color.BLACK;
    public static float current_width = 20f;
    public static int paintAlpha = 255;
    private Bitmap canvasBitmap;


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
        paint_brush.setAntiAlias(true);
        paint_brush.setColor(Color.BLACK);
        paint_brush.setStyle(Paint.Style.STROKE);
        paint_brush.setStrokeCap(Paint.Cap.ROUND);
        paint_brush.setStrokeJoin(Paint.Join.ROUND);
        paint_brush.setStrokeWidth(50f);
        //added this
        paint_brush.setAlpha(255);
        //alphaList.add(255);

        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                pathList.add(path);
                colorList.add(current_brush_color);
                widthList.add(current_width);
                alphaList.add(paintAlpha);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        //respond to down, move and up events
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                //drawCanvas.drawPath(drawPath, drawPath);
                path.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }*/

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

    /*@Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, paint_brush);
        canvas.drawPath(path, paint_brush);
    }*/

}
