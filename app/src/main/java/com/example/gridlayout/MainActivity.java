package com.example.gridlayout;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import java.util.Random;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMN_COUNT = 10;
    //inital counter for clock
    private int clock = 0;

    public String timeS;

    private int flag_count = 4;
    private boolean bomb = false;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;
    private ArrayList<TextView> flags;
    public ArrayList<TextView> bombs = new ArrayList<TextView>();

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
                tv.setText("0");
                tv.setTextColor(Color.GREEN);
                tv.setOnClickListener(this::onClickTV);//calls the function that handles when things are clicked
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.setMargins(dpToPixel(2), dpToPixel(2), dpToPixel(2), dpToPixel(2));
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);
                grid.addView(tv, lp);
                cell_tvs.add(tv);
            }
        }

        //calling the randomizer for the bombs
        randomizer();
    }

//    public void sendMessage(){
//        TextView timeee = (TextView) findViewById(R.id.text_counter);
//        String message = timeee.getText().toString();
//        Intent intent = new Intent(this, EndScreen.class);
//        intent.putExtra("com.example.gridlayout.MESSAGE", message);
//        startActivity(intent);
//    }

    private void randomizer(){
        Random ran = new Random();
        int count = 0;

        for (int k = 0; k < 4; k++)
        {
            int cell = ran.nextInt(100);

            for (int n=0; n<cell_tvs.size(); n++) {
                if(n == cell)
                {
                    bombs.add(cell_tvs.get(n));
                    cell_tvs.get(n).setBackground(getResources().getDrawable(R.drawable.baseline_upcoming_24));
                    cell_tvs.get(n).setBackgroundColor(Color.GREEN);
                    cell_tvs.get(n).setText(" ");
                    setCounts(cell_tvs.get(n), n);

                }
            }
        }
    }

    private void setCounts(TextView tv, int n){
        cell_tvs.get(n-9).setText("1");
        cell_tvs.get(n-10).setText("1");
        cell_tvs.get(n-11).setText("1");
        cell_tvs.get(n+9).setText("1");
        cell_tvs.get(n+10).setText("1");
        cell_tvs.get(n+11).setText("1");
        cell_tvs.get(n-1).setText("1");
        cell_tvs.get(n+1).setText("1");

    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.text_counter);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (clock%3600) / 60;
                int seconds = clock%60;
                timeS = String.format("%02d:%02d", minutes, seconds);
                timeView.setText(timeS);
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

    public void checker(TextView tv){
        for (int i = 0; i < bombs.size(); i++)
        {
            if (tv == bombs.get(i))
            {
                bomb = true;
//                sendMessage();
            }
        }
    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int n = findIndexOfCellTextView(tv);
        int i = n/COLUMN_COUNT;
        int j = n%COLUMN_COUNT;
        checker(tv);
        if (flag == true)
        {
            if(flag_count != 0 && tv.getCurrentTextColor() != Color.BLACK)
            {
                tv.setBackgroundColor(Color.parseColor("lime"));
                tv.setBackground(getResources().getDrawable(R.drawable.flag_img));
                TextView cnt = (TextView) findViewById(R.id.flag_count);
                flag_count--;
                cnt.setText(String.valueOf(flag_count));
                tv.setText(" ");
            }
        }
//        tv.setText(String.valueOf(i)+String.valueOf(j));
        else if(bomb == false){
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.LTGRAY);
        }
    }
}