package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class DriverHomeActivity extends AppCompatActivity {

    TextView driverNameTV;
    ImageView driverIV;
    ImageView logoutIV;
    CardView currentCV;
    CardView myRidesCV;
    CardView viewFeedbacksCV;
    CardView viewPaymentsCV;

    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);


        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        driverNameTV = findViewById(R.id.driverNameTextView);
        driverIV = findViewById(R.id.driverIconImageView);
        logoutIV = findViewById(R.id.drLogoutImageView);
        currentCV = findViewById(R.id.card_currentRides);
        myRidesCV = findViewById(R.id.card_myRides);
        viewFeedbacksCV = findViewById(R.id.card_viewFeedbacks);
        viewPaymentsCV = findViewById(R.id.card_payments);


        if (!uid.equals("")){
            getDriverDetails();
        }

        currentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverHomeActivity.this,OnTripActivity.class);
                startActivity(intent);

            }
        });

        myRidesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverHomeActivity.this,MyRidesActivity.class);
                startActivity(intent);

            }
        });

        viewFeedbacksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverHomeActivity.this,ViewFeedbackActivity.class);
                startActivity(intent);

            }
        });

        viewPaymentsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverHomeActivity.this,ViewPaymentsActivity.class);
                startActivity(intent);

            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });

        driverIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverHomeActivity.this,DriverProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getDriverDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/getDriverDetails.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray("data");
                    JSONObject jsonObject = array.getJSONObject(0);

                    String name = jsonObject.getString("name");
                    String image = jsonObject.getString("image");

                    driverNameTV.setText(name);

                    if (!image.equals("")) {
                        Glide.with(getApplicationContext())
                                .load("http://" + ip + "/eriksha/driver_tbl/uploads/" + image)
                                .into(driverIV);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DriverHomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DriverHomeActivity.this);
        requestQueue.add(stringRequest);

    }

    private void logout() {

        new AlertDialog.Builder(DriverHomeActivity.this)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(DriverHomeActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}