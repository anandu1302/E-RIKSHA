package com.nextgen.eriksha;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nextgen.eriksha.databinding.ActivityRequestBookingBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RequestBookingActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "RequestBookingActivity";

    private TextView originTextView, destinationTextView;
    TextView sendRequestTV;

    private double lat;
    private double longi;
    String destination;

    private GlobalPreference globalPreference;
    String ip,uid;
    private GoogleMap mMap;
    private ActivityRequestBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRequestBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(this);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();


        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude",0);
        longi = intent.getDoubleExtra("longitude",0);
        destination = intent.getStringExtra("destination");

        Log.d(TAG, "onCreate: "+lat +" "+longi);


        originTextView =  findViewById(R.id.rOriginTV);
        destinationTextView = findViewById(R.id.rDestinationTV);
        sendRequestTV = findViewById(R.id.sendRequestTextView);


        originTextView.setText(getAddress(lat,longi));
        destinationTextView.setText(destination);

        sendRequestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendRequest();
            }
        });


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        BitmapDescriptor icon3 = BitmapDescriptorFactory.fromResource(R.drawable.location_one);
        LatLng sydney3 = new LatLng(lat, longi);
        MarkerOptions markerOptions3 = new MarkerOptions().position(sydney3)
                .title("Marker in Bharuch")
                .snippet("snippet snippet snippet snippet snippet...")
                .icon(icon3);
        mMap.addMarker(markerOptions3);

        LatLng origin = new LatLng(lat, longi);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                origin).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapActivity", "Can't find style. Error: ", e);
        }

    }

    private void sendRequest() {

        String startLoc = getLocation(lat,longi);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/sendRequest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: sendRequest" + response);
                
                if (response.equals("success")){

                    sendRequestTV.setText("Cancel Request");

                    Intent intent = new Intent(RequestBookingActivity.this,UserHomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("start", String.valueOf(startLoc));
                params.put("destination", destination);
                params.put("startlat", String.valueOf(lat));
                params.put("startlon", String.valueOf(longi));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RequestBookingActivity.this);
        requestQueue.add(stringRequest);
    }

    public String getLocation(double lat, double lng) {

        Geocoder geocoder = new Geocoder(RequestBookingActivity.this, Locale.getDefault());
        String location = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            location = obj.getSubLocality();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return location;
    }


    public String getAddress(double lat, double lng) {

        Geocoder geocoder = new Geocoder(RequestBookingActivity.this, Locale.getDefault());
        String address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            String[] splited = add.split(",");
            address = splited[0] + ", " + splited[1] + "\n" + splited[2] + ", " + splited[3] + ", " + splited[4];

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return address;
    }
}