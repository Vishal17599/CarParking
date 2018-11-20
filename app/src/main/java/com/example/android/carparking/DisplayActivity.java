package com.example.android.carparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent in = getIntent();
        ArrayList<String> al = in.getStringArrayListExtra("key");
        TextView tv = findViewById(R.id.textbox);
        String display = "Name : "+al.get(0)+"\n"+"Your Parking Spot is : "+al.get(1)+"\n"+"Vehicle no. : "+al.get(3);
        tv.setText(display);
    }
}
