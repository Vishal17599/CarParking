package com.example.android.carparking;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    ArrayList<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent in = getIntent();
        al = in.getStringArrayListExtra("key");
        Log.v("Hello",al.get(4));
        TextView tv = findViewById(R.id.textbox);
        final String display = "Name : "+al.get(0)+"\n"+"Your Parking Spot is : "+al.get(1)+"\n"+"Vehicle no. : "+al.get(3);
        tv.setText(display);
        Button bd = findViewById(R.id.butsend);
        bd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                String []addresses = new String[100];
                addresses[0]= al.get(4);
                intent.putExtra(Intent.EXTRA_EMAIL,addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Information regarding Parking");
                intent.putExtra(Intent.EXTRA_TEXT,display);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
}
