package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.eriksha.DriverHomeActivity;
import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.IncomingRequestModelClass;
import com.nextgen.eriksha.OnTripActivity;
import com.nextgen.eriksha.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomingRequestAdapter  extends RecyclerView.Adapter<IncomingRequestAdapter.MyViewHolder>{

    private String TAG ="IncomingRequestAdapter";

    ArrayList<IncomingRequestModelClass> list;
    Context context;
    String ip,uid;

    public IncomingRequestAdapter(ArrayList<IncomingRequestModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_incoming_requests,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        IncomingRequestModelClass requestList = list.get(position);
        holder.pickUpTV.setText(requestList.getPickup());
        holder.dropOffTV.setText(requestList.getDropoff());
        holder.nameTV.setText(requestList.getName());
        holder.numberTV.setText(requestList.getNumber());

        holder.acceptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acceptRequest(requestList.getId());
            }
        });

    }

    private void acceptRequest(String requestId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/acceptRequest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, OnTripActivity.class);
                    context.startActivity(intent);

                }else{

                    Toast.makeText(context, "Failed to accept", Toast.LENGTH_SHORT).show();
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("requestId",requestId);
                params.put("uid",uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pickUpTV;
        TextView dropOffTV;
        TextView nameTV;
        TextView numberTV;
        TextView acceptTV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            pickUpTV = itemview.findViewById(R.id.ridePickUpTextView);
            dropOffTV = itemview.findViewById(R.id.rideDestinationTextView);
            nameTV = itemview.findViewById(R.id.ridePassengerNameTextView);
            numberTV = itemview.findViewById(R.id.ridePassengerNumberTextView);
            acceptTV = itemview.findViewById(R.id.acceptTextView);



        }
    }
}
