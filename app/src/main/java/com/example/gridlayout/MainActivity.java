package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMN_COUNT = 2;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cell_tvs = new ArrayList<TextView>();

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
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        for (int i = 0; i<=10; i++) { //rows
            for (int j=0; j<=1; j++) { //coloumns

                TextView tv = new TextView(this); //dynamic cell creration
                tv.setHeight( dpToPixel(32) ); //height
                tv.setWidth( dpToPixel(32) ); //width
                tv.setTextSize( 16 );//dpToPixel(32) ); //text size
                tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER); //center text
                tv.setTextColor(Color.GRAY); //text color
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

//        // Method (3): add four dynamically created cells with LayoutInflater
//        LayoutInflater li = LayoutInflater.from(this);
//        for (int i = 10; i<=11; i++) {
//            for (int j=2; j<=3; j++) {
//                TextView tv = (TextView) li.inflate(R.layout.custom_cell_layout, grid, false);
//                //tv.setText(String.valueOf(i)+String.valueOf(j));
//                tv.setTextColor(Color.GRAY);
//                tv.setBackgroundColor(Color.GRAY);
//                tv.setOnClickListener(this::onClickTV);
//
//                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tv.getLayoutParams();
//                lp.rowSpec = GridLayout.spec(i);
//                lp.columnSpec = GridLayout.spec(j);
//
//                grid.addView(tv, lp);
//
//                cell_tvs.add(tv);
//            }
//        }

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