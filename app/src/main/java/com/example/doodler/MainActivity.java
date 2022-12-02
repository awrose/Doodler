package com.example.doodler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.doodler.DoodleView.current_brush_color;
import static com.example.doodler.DoodleView.pathList;
import static com.example.doodler.DoodleView.colorList;
import static com.example.doodler.DoodleView.current_width;
import static com.example.doodler.DoodleView.widthList;

public class MainActivity extends AppCompatActivity {
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    //Context context;
    //DoodleView doodle = new DoodleView(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DoodleView doodle = new DoodleView(this);

        Button ColorBtn= (Button) findViewById(R.id.ColorBtn);
        ColorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                paint_brush.setColor(Color.MAGENTA);
                currentColor(paint_brush.getColor());
            }
        });

        Button increaseBrushWidth= (Button) findViewById(R.id.increaseBrushSize);
        increaseBrushWidth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                paint_brush.setStrokeWidth(200f);
                currentWidth(paint_brush.getStrokeWidth());
            }
        });

        Button clearCanvasBtn= (Button) findViewById(R.id.clearCanvasBtn);
        clearCanvasBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                widthList.clear();
                pathList.clear();
                colorList.clear();
                path.reset();
            }
        });






    }

    public void currentColor(int c){
        current_brush_color = c;
        path = new Path();
    }

    public void currentWidth(float f){
        current_width = f;
        path = new Path();
    }




    }