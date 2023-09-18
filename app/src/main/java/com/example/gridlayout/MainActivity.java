package com.example.gridlayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import java.util.Random;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMN_COUNT = 10;
    //inital counter for clock
    private int clock = 0;

    private int flag_count = 4;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;
    private ArrayList<TextView> flags;

    //for the button used to dig and flag
    ImageButton btn;
    private boolean flag;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //arraylist to hold all the cells from the grid
        cell_tvs = new ArrayList<TextView>();

        //Start the timer for the game
        runTimer();

        //Digging and flagging functionality button
        runButton();

        //Creating all the cells on the grid
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i<=11; i++) { //rows
            for (int j = 0; j <= 9; j++) { //coloumns
                TextView tv = new TextView(this); //dynamic cell creation
                tv.setHeight(dpToPixel(30)); //height
                tv.setWidth(dpToPixel(30)); //width
                tv.setTextSize(16);//dpToPixel(32) ); //text size
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER); //center text
                tv.setBackgroundColor(Color.GREEN); //background color
                tv.setOnClickListener(this::onClickTV);//calls the function that handles when things are clicked
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                grid.addView(tv, lp);
                cell_tvs.add(tv);
            }
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.text_counter);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (clock%3600) / 60;
                int seconds = clock%60;
                String time = String.format("%02d:%02d", minutes, seconds);
                timeView.setText(time);
                clock++;
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void runButton(){
        //creating the button
        btn = findViewById(R.id.imgbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag ==  true)
                {
                    flag = false;
                    btn.setImageDrawable(getResources().getDrawable(R.drawable.brush_24));
                }
                else {
                    flag = true;
                    btn.setImageDrawable(getResources().getDrawable(R.drawable.flag_img));
                }
            }
        });
    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;
        if (flag == true)
        {
            if(flag_count != 0)
            {
                tv.setBackgroundColor(Color.parseColor("lime"));
                tv.setBackground(getResources().getDrawable(R.drawable.flag_img));
                TextView cnt = (TextView) findViewById(R.id.flag_count);
                flag_count--;
                cnt.setText(String.valueOf(flag_count));
            }
        }
//        tv.setText(String.valueOf(i)+String.valueOf(j));
        else {
            tv.setBackgroundColor(Color.LTGRAY);
        }
    }
}