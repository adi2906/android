package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinishActivity extends AppCompatActivity {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    TextView tv;
    TextView tv3;
    TextView tv2;
    TextView tv4;
    TextView tv5;
    TextView tv8;
    Date date = new Date();
    public int minute2 = date.getMinutes();
    public int seconds2 = date.getSeconds();
    int a = MainActivity.minute1;
    int b = MainActivity.sec;

    private static final String FINISH_KEY = "stars";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        tv = findViewById(R.id.tv_finish);
        tv2 = findViewById(R.id.tv_finish2);
        tv3 = findViewById(R.id.tv_finish3);
        tv4 = findViewById(R.id.tv_finish6);
        tv5 = findViewById(R.id.tv_finish5);
        tv8 = findViewById(R.id.tv_finish8);

        //Log.i("ora2", String.valueOf((minute2 - a)));
        int minutes=minute2 - a;
        int seconds=seconds2 - b;

        Bundle bundle = getIntent().getExtras();
        float stars = bundle.getFloat(FINISH_KEY);
        //Log.i("stars", String.valueOf(stars));

        tv.setText(R.string.finish2);
        tv2.setText(String.valueOf(minutes));
        tv3.setText(R.string.minute);
        tv4.setText(String.valueOf(seconds));
        tv5.setText(R.string.secunde);
        tv8.setText(String.valueOf(stars));



    }
}