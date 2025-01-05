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
import com.nextgen.eriksha.Adapter.MyRidesAdapter;
import com.nextgen.eriksha.ModelClass.MyRidesModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyRidesActivity extends AppCompatActivity {

    private static String TAG ="MyRidesActivity";

    RecyclerView myRidesRV;
    ArrayList<MyRidesModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private ImageView BackIV;
    private TextView appBarTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rides);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        BackIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);
        appBarTV.setText("My Rides");

        myRidesRV = findViewById(R.id.myRidesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRidesRV.setLayoutManager(layoutManager);

        BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyRidesActivity.this,DriverHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewMyRides();
    }

    private void viewMyRides() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/eriksha/api/viewMyRides.php?did="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(MyRidesActivity.this, "No Feedbacks Available", Toast.LENGTH_SHORT).show();
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
                            String name = object.getString("userName");
                            String number = object.getString("userNumber");

                            list.add(new MyRidesModelClass(id,start,destination,amount,date,status,name,number));

                        }

                        MyRidesAdapter adapter = new MyRidesAdapter(list,MyRidesActivity.this);
                        myRidesRV.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}