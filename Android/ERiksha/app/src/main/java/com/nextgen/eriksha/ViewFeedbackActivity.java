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
import com.nextgen.eriksha.Adapter.FeedbackAdapter;
import com.nextgen.eriksha.ModelClass.FeedbackModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFeedbackActivity extends AppCompatActivity {

    private static String TAG ="ViewFeedbacksActivity";

    RecyclerView feedbacksRV;
    ArrayList<FeedbackModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private ImageView BackIV;
    private TextView appBarTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        feedbacksRV = findViewById(R.id.feedbacksRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        feedbacksRV.setLayoutManager(layoutManager);

        BackIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);
        appBarTV.setText("View Feedbacks");

        BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewFeedbackActivity.this,DriverHomeActivity.class);
                startActivity(intent);
            }
        });

        getFeedbacks();
    }

    private void getFeedbacks() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/eriksha/api/viewFeedbacks.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(ViewFeedbackActivity.this, "No Feedbacks Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String rating = object.getString("rating");
                            String feedback = object.getString("feedback");


                            list.add(new FeedbackModelClass(id,name,rating,feedback));

                        }

                        FeedbackAdapter adapter = new FeedbackAdapter(list,ViewFeedbackActivity.this);
                        feedbacksRV.setAdapter(adapter);

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