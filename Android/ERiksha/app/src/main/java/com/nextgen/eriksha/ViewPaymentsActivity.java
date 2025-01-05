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
import com.nextgen.eriksha.Adapter.PaymentAdapter;
import com.nextgen.eriksha.ModelClass.PaymentModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPaymentsActivity extends AppCompatActivity {

    private static String TAG ="ViewPaymentsActivity";

    RecyclerView paymentsRV;
    ArrayList<PaymentModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private ImageView BackIV;
    private TextView appBarTV;
    TextView totalAccountBalanceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        paymentsRV = findViewById(R.id.viewPaymentsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        paymentsRV.setLayoutManager(layoutManager);

        BackIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);
        appBarTV.setText("View Transactions");

        totalAccountBalanceTV = findViewById(R.id.totalAccountBalanceTextView);

        BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewPaymentsActivity.this,DriverHomeActivity.class);
                startActivity(intent);
            }
        });

        getPayments();
    }

    private void getPayments() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/eriksha/api/viewPayments.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(ViewPaymentsActivity.this, "No Payments Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String amount = object.getString("amount");
                            String date = object.getString("date");
                            String cardnumber = object.getString("card_number");
                            String userName = object.getString("name");
                            String userNumber = object.getString("number");
                            String totalAmount = object.getString("totalAmount");

                            totalAccountBalanceTV.setText("₹ "+totalAmount);


                            list.add(new PaymentModelClass(id,amount,date,cardnumber,userName,userNumber));

                        }

                        PaymentAdapter adapter = new PaymentAdapter(list,ViewPaymentsActivity.this);
                        paymentsRV.setAdapter(adapter);

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