package com.example.android.carparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edname,edcar,edemail;
    String name,carno,email;
    String id,spot;
    JSONObject jason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edname = findViewById(R.id.name);
        edcar = findViewById(R.id.carno);
        edemail = findViewById(R.id.email);
        AndroidNetworking.initialize(getApplicationContext());

        Button b = findViewById(R.id.but);
        b.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.performClick();
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functionality(view);
            }
        });
    }
    public void functionality(View view)
    {
        name = edname.getText().toString();
        carno = edcar.getText().toString();
        email = edemail.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("numberPlate", carno);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post("http://parking-car.herokuapp.com/newparking")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

        AndroidNetworking.get("http://parking-car.herokuapp.com/getuser/"+carno)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject object = response;
                try {
                    String name = response.getString("name");
                    String parkingspot = response.getString("parkingNumber");
                    String parkingtime = response.getString("parkingTime");
                    String carno = response.getString("numberPlate");
                    ArrayList<String> al = new ArrayList<>();
                    al.add(name);
                    al.add(parkingspot);
                    al.add(parkingtime);
                    al.add(carno);
                    al.add(email);
                    Intent in = new Intent(MainActivity.this,DisplayActivity.class);
                    in.putExtra("key",al);
                    startActivity(in);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError anError) {

            }
        }
        );
    }
}
