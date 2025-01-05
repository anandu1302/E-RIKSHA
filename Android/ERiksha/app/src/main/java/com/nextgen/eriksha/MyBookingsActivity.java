package com.nextgen.eriksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.eriksha.Adapter.BookingsAdapter;
import com.nextgen.eriksha.ModelClass.BookingsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyBookingsActivity extends AppCompatActivity {


    private static String TAG ="MyBookingsActivity";

    RecyclerView myBookingsRV;
    ArrayList<BookingsModelClass> list;

    private GlobalPreference globalPreference;
    String ip,uid;
    private ImageView BackImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        BackImageButton = (ImageView) findViewById(R.id.bookingsBackImageView);
        BackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyBookingsActivity.this,UserDashboardActivity.class);
                startActivity(intent);
            }
        });

        myBookingsRV = findViewById(R.id.myBookingsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myBookingsRV.setLayoutManager(layoutManager);

        getBookings();
    }

    private void getBookings() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/eriksha/api/myBookings.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(MyBookingsActivity.this, "No Rides Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String start = object.getString("start_location");
                            String destination = object.getString("dest_location");
                            String amount = object.getString("trip_amount");
                            String date = object.getString("trip_date");
                            String status = object.getString("status");
                            String driverId = object.getString("driverId");
                            String driverName = object.getString("driverName");


                            list.add(new BookingsModelClass(id,start,destination,amount,date,status,driverId,driverName));

                        }

                        BookingsAdapter adapter = new BookingsAdapter(list,MyBookingsActivity.this);
                        myBookingsRV.setAdapter(adapter);

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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}