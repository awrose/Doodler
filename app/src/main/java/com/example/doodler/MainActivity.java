package com.example.doodler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import static com.example.doodler.DoodleView.current_brush_color;
import static com.example.doodler.DoodleView.pathList;
import static com.example.doodler.DoodleView.colorList;
import static com.example.doodler.DoodleView.current_width;
import static com.example.doodler.DoodleView.widthList;
import static com.example.doodler.DoodleView.paintAlpha;
import static com.example.doodler.DoodleView.alphaList;

public class MainActivity extends AppCompatActivity {
    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    private DoodleView doodle;
    private Button opacityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doodle = (DoodleView)findViewById(R.id.doodleView);

        opacityBtn = (Button) findViewById(R.id.opacity_btn);
        opacityBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(v.getId() == R.id.opacity_btn){
                    final Dialog seekDialog = new Dialog(MainActivity.this);
                    seekDialog.setTitle("Opacity level:");
                    seekDialog.setContentView(R.layout.opacity_chooser);

                    final TextView seekTxt = (TextView)seekDialog.findViewById(R.id.opq_txt);
                    final SeekBar seekOpq = (SeekBar)seekDialog.findViewById(R.id.opacity_seek);

                    seekOpq.setMax(100);

                    int currLevel = Math.round((float)paint_brush.getAlpha()/255*100);
                    //int currLevel = doodle.getPaintAlpha();

                    seekTxt.setText(currLevel+"%");
                    seekOpq.setProgress(currLevel);

                    seekOpq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            seekTxt.setText(progress +"%");
                            paint_brush.setAlpha(progress);
                            currentOpacity(paint_brush.getAlpha());
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });

                    Button opqBtn = (Button)seekDialog.findViewById(R.id.opq_ok);

                    opqBtn.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v){
                            seekDialog.dismiss();
                        }
                    });
                    seekDialog.show();
                }
            }
        });

        Button colorBtn = (Button) findViewById(R.id.ColorBtn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(v.getId() == R.id.ColorBtn){
                    final Dialog colorDialog = new Dialog(MainActivity.this);
                    colorDialog.setTitle("Choose A Color:");
                    colorDialog.setContentView(R.layout.color_chooser);

                    final RadioGroup radioGroup = (RadioGroup)colorDialog.findViewById(R.id.radioGroup);

                    final int[] chosenColor = {Color.BLACK};

                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            RadioButton radioButton = (RadioButton)radioGroup.findViewById(i);

                            if(i == R.id.gray){
                                chosenColor[0] = Color.GRAY;
                            }else if(i == R.id.green){
                                chosenColor[0] = Color.GREEN;
                            }else if(i == R.id.red){
                                chosenColor[0] = Color.RED;
                            }else if(i == R.id.yellow){
                                chosenColor[0] = Color.YELLOW;
                            }else if(i == R.id.magenta){
                                chosenColor[0] = Color.MAGENTA;
                            }else if(i == R.id.blue){
                                chosenColor[0] = Color.BLUE;
                            }
                        }
                    });

                    Button closeBtn = (Button)colorDialog.findViewById(R.id.colorPicker_close);

                    closeBtn.setOnClickListener(new OnClickListener(){
                        @Override
                        public void onClick(View v){
                            paint_brush.setColor(chosenColor[0]);
                            currentColor(paint_brush.getColor());
                            colorDialog.dismiss();
                        }
                    });
                    colorDialog.show();
                }
            }
        });


        Button increaseBrushWidth= (Button) findViewById(R.id.increaseBrushSize);
        increaseBrushWidth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                paint_brush.setStrokeWidth((float)paint_brush.getStrokeWidth() + 10);
                currentWidth(paint_brush.getStrokeWidth());
            }
        });

        Button decreaseBrushWidth= (Button) findViewById(R.id.decreaseBrushSize);
        decreaseBrushWidth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                paint_brush.setStrokeWidth((float)paint_brush.getStrokeWidth() - 10);
                currentWidth(paint_brush.getStrokeWidth());
            }
        });

        Button clearCanvasBtn= (Button) findViewById(R.id.clearCanvasBtn);
        clearCanvasBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                widthList.clear();
                alphaList.clear();
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

    public void currentOpacity(int i){
        paintAlpha = i;
        System.out.println(paintAlpha);
        path = new Path();
    }

}