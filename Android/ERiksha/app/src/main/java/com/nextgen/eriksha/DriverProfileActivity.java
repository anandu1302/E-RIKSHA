package com.nextgen.eriksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverProfileActivity extends AppCompatActivity {

    private static final String TAG = "DriverProfileActivity";

    TextView nameTV;
    TextView numberTV;
    TextView vehicleNoTV;
    TextView emailTV;
    ImageView backIV;

    GlobalPreference globalPreference;
    private String ip;
    private String uid;
    private String image = "";

    private ImageView driverIV;
    private SwitchCompat customSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        nameTV = findViewById(R.id.pDriverNameTextView);
        numberTV = findViewById(R.id.pDriverPhoneNumberTextView);
        vehicleNoTV = findViewById(R.id.pDriverVehicleNoTextView);
        emailTV = findViewById(R.id.pDriverEmailTextView);
        backIV = findViewById(R.id.pDriverBackImageView);


        driverIV = findViewById(R.id.pDriverImageView);

        customSwitch = (SwitchCompat) findViewById(R.id.customSwitch);
        customSwitch.setChecked(true);
        customSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {

                if(status)
                    changeDriverStatus("active");
                else
                    changeDriverStatus("Inactive");

            }
        });

        getDriverDetails();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverProfileActivity.this,DriverHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDriverDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/driverProfile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                try {

                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("data");
                    JSONObject data = array.getJSONObject(0);

                    String name = data.getString("name");
                    String number = data.getString("number");
                    String email = data.getString("email");
                    String vehicle_no = data.getString("vehicle_no");
                    String image = data.getString("image");
                    String driver_status = data.getString("driver_status");

                    if(driver_status.equals("active"))
                        customSwitch.setChecked(true);
                    else
                        customSwitch.setChecked(false);

                    Glide.with(getApplicationContext())
                            .load("http://"+ip+"/eriksha/driver_tbl/uploads/" +image)
                            .circleCrop()
                            .into(driverIV);

                    nameTV.setText(name);
                    numberTV.setText(number);
                    emailTV.setText(email);
                    vehicleNoTV.setText(vehicle_no);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void changeDriverStatus(String status) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+ "/eriksha/api/driverStatusUpdate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DriverProfileActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("status",status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}