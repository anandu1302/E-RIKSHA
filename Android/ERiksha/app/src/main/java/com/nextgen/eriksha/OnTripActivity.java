package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.eriksha.Service.LocationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnTripActivity extends AppCompatActivity {

    private static final String TAG = "OnTripActivity";


    TextView startLocTV;
    TextView destinationTV;
    TextView nameTV;
    TextView numberTV;
    TextView startRideTV;
    TextView finishRideTV;
    TextView getDirectionsTV;
    LinearLayout callLL;
    LinearLayout onTripLL;
    LinearLayout noTripLL;
    ImageView backIV;

    String tripId;
    String latitude,longitude;
    private GlobalPreference globalPreference;
    private String ip,uid;

    String passNumber;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_trip);

        Intent lIntent = new Intent(OnTripActivity.this, LocationService.class);
        startService(lIntent);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
        latitude = globalPreference.getLatitude();
        longitude = globalPreference.getLongitude();

        startLocTV = findViewById(R.id.tripStartTextView);
        destinationTV = findViewById(R.id.tripDestinationTextView);
        nameTV = findViewById(R.id.tPassengerNameTextView);
        numberTV = findViewById(R.id.tPassengerNumberTextView);
        startRideTV = findViewById(R.id.startRideTextView);
        finishRideTV = findViewById(R.id.finishRideTextView);
        callLL = findViewById(R.id.callLinearLayout);
        onTripLL = findViewById(R.id.onTripLL);
        noTripLL = findViewById(R.id.noTripLinearLayout);
        backIV = findViewById(R.id.tripBackImageView);
        getDirectionsTV = findViewById(R.id.getDirectionsTV);


        getTripDetails();

        startRideTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDirectionsTV.setVisibility(View.GONE);

                startRide(tripId);
            }
        });

        finishRideTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(OnTripActivity.this, latitude+" "+longitude, Toast.LENGTH_SHORT).show();

                finishRide();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OnTripActivity.this,DriverHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent callIntent;


                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + passNumber));

                Log.d("TAG", "callfn: "+"inside call fn"+passNumber);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Request the permission
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
                } else {
                    // Permission is granted, proceed with call
                   startActivity(callIntent);
                }

            }
        });

    }




    private void getTripDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/getTripDetails.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    onTripLL.setVisibility(View.GONE);
                    noTripLL.setVisibility(View.VISIBLE);
                }
                else{

                    onTripLL.setVisibility(View.VISIBLE);
                    noTripLL.setVisibility(View.GONE);

                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            tripId = object.getString("id");
                            String startLocation = object.getString("start_location");
                            String destLocation = object.getString("dest_location");
                            String startLatitude = object.getString("start_latitude");
                            String startLongitude = object.getString("start_longitude");
                            String passName = object.getString("name");
                             passNumber = object.getString("number");
                            String trip_status = object.getString("trip_status");

                            startLocTV.setText(startLocation);
                            destinationTV.setText(destLocation);
                           // nameTV.setText(passName);
                            numberTV.setText(passNumber);

                            if (passName != null && !passName.isEmpty()) {
                                String passengerName = Character.toUpperCase(passName.charAt(0)) + passName.substring(1);
                                Log.d("Capitalized Word", passengerName);
                                nameTV.setText(passengerName);
                            }

                            if (trip_status.equals("onTrip")) {

                                finishRideTV.setVisibility(View.VISIBLE);
                                startRideTV.setVisibility(View.GONE);
                                getDirectionsTV.setVisibility(View.GONE);
                            }else{

                                finishRideTV.setVisibility(View.GONE);
                                startRideTV.setVisibility(View.VISIBLE);
                            }

                            //to track user location by driver
                            getDirectionsTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                                            Uri.parse("http://maps.google.com/maps?daddr="+startLatitude+","+startLongitude));
                                    startActivity(mintent);


                                }
                            });


                        }

                    } catch(JSONException e){
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
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(OnTripActivity.this);
        requestQueue.add(stringRequest);
    }

    private void startRide(String tId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/startTrip.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("success")){

                    Intent intent = new Intent(OnTripActivity.this,OnTripActivity.class);
                    startActivity(intent);
                    finish();


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
                params.put("tripId",tId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(OnTripActivity.this);
        requestQueue.add(stringRequest);


    }

    private void finishRide() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/finishRide.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (!response.equals("")){

                    Intent intent = new Intent(OnTripActivity.this,FinishRideActivity.class);
                    intent.putExtra("response",response);
                    startActivity(intent);
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
                params.put("uid",uid);
                params.put("tripId",tripId);
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(OnTripActivity.this);
        requestQueue.add(stringRequest);

    }
}