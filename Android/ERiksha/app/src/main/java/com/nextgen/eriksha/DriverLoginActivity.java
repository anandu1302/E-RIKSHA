package com.nextgen.eriksha;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DriverLoginActivity extends AppCompatActivity {

    private static String TAG = "DriverLoginActivity";

    EditText emailET;
    EditText passwordET;
    Button loginBT;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        emailET = findViewById(R.id.dEmailEditText);
        passwordET = findViewById(R.id.dPasswordEditText);
        loginBT = findViewById(R.id.dLoginButton);
        backIV =  findViewById(R.id.dLoginBackImageView);

      //  emailET.setText("amal@gmail.com");
       // passwordET.setText("123");

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dLogin();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverLoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    private void dLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/driverLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(DriverLoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String id = jsonObject.getString("id");
                        globalPreference.saveID(id);
                        String name = jsonObject.getString("name");
                        globalPreference.saveName(name);
                        String ride_status = jsonObject.getString("ride_status");
                        String driver_status = jsonObject.getString("driver_status");

                        Log.d(TAG, "onRideStatus: "+ride_status);

                        //checking the driver status and redirecting
                        if (!driver_status.equals("active")) {

                            Intent intent = new Intent(DriverLoginActivity.this,DriverHomeActivity.class);
                            startActivity(intent);

                        }
                        else if (ride_status.equals("waiting")){
                            Intent intent = new Intent(DriverLoginActivity.this,DriverMapActivity.class);
                            startActivity(intent);

                        } else if (ride_status.equals("accepted") || ride_status.equals("onTrip")) {

                            Intent intent = new Intent(DriverLoginActivity.this,OnTripActivity.class);
                            intent.putExtra("rideStatus",ride_status);
                            startActivity(intent);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);

            }
        }){
            @Override
            @Nullable
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",emailET.getText().toString());
                params.put("password",passwordET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DriverLoginActivity.this);
        requestQueue.add(stringRequest);
    }
}