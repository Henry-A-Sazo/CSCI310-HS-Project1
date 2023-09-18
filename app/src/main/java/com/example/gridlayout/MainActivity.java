package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

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

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;

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

//        // Method (1): add statically created cells
//        TextView tv00 = (TextView) findViewById(R.id.textView00);
//        TextView tv01 = (TextView) findViewById(R.id.textView01);
//        TextView tv10 = (TextView) findViewById(R.id.textView10);
//        TextView tv11 = (TextView) findViewById(R.id.textView11);
////        TextView tv20 = (TextView) findViewById(R.id.textView20);
//
//        tv00.setTextColor(Color.GRAY);
//        tv00.setBackgroundColor(Color.GRAY);
//        tv00.setOnClickListener(this::onClickTV);
//
//        tv01.setTextColor(Color.GRAY);
//        tv01.setBackgroundColor(Color.GRAY);
//        tv01.setOnClickListener(this::onClickTV);
//
//        tv10.setTextColor(Color.GRAY);
//        tv10.setBackgroundColor(Color.GRAY);
//        tv10.setOnClickListener(this::onClickTV);
//
//        tv11.setTextColor(Color.GRAY);
//        tv11.setBackgroundColor(Color.GRAY);
//        tv11.setOnClickListener(this::onClickTV);
//
////        tv20.setTextColor(Color.GRAY);
////        tv20.setBackgroundColor(Color.GRAY);
////        tv20.setOnClickListener(this::onClickTV);
//
//        cell_tvs.add(tv00);
//        cell_tvs.add(tv01);
//        cell_tvs.add(tv10);
//        cell_tvs.add(tv11);
////        cell_tvs.add(tv20);

        // Method (2): add four dynamically created cells
        //Creating all the cells on the grid
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i<=11; i++) { //rows
            for (int j = 0; j <= 9; j++) { //coloumns
                TextView tv = new TextView(this); //dynamic cell creation
                tv.setHeight(dpToPixel(30)); //height
                tv.setWidth(dpToPixel(30)); //width
//                tv.setTextSize(16);//dpToPixel(32) ); //text size
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER); //center text
                tv.setTextColor(Color.WHITE); //text color
                tv.setBackgroundColor(Color.GRAY); //background color
                tv.setOnClickListener(this::onClickTV);

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
        tv.setText(String.valueOf(i)+String.valueOf(j));
        if (tv.getCurrentTextColor() == Color.GRAY) {
            tv.setTextColor(Color.GREEN);
            tv.setBackgroundColor(Color.parseColor("lime"));
        }else {
            tv.setTextColor(Color.GRAY);
            tv.setBackgroundColor(Color.LTGRAY);
        }
    }
}