package com.example.android.carparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText edname,edcar,edemail;
    String name,carno,email;
    String id,spot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edname = findViewById(R.id.name);
        edcar = findViewById(R.id.carno);
        edemail = findViewById(R.id.email);
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
        AndroidNetworking.post("http://parking-car.herokuapp.com/allspots")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build();
        AndroidNetworking.get("http://parking-car.herokuapp.com/getuser/"+edcar).build().getAsJSONArray(new JSONArrayRequestListener() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                JSONObject jobj = response.getJSONObject(0);
                System.out.println("Response: \n"+jobj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        @Override
        public void onError(ANError error) {
            // handle error
        }
    }); ;
    }
}
