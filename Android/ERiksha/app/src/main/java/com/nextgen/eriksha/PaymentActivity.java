package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.nextgen.eriksha.Adapter.MyAccountAdapter;
import com.nextgen.eriksha.ModelClass.MyAccountModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static String TAG ="PaymentActivity";

    TextView amountTV;
    TextView dateTV;
    EditText cardNameET;
    EditText cardNumberET;
    EditText cvvET;
    Button makePaymentBT;

    RecyclerView accountsRV;
    ArrayList<MyAccountModelClass> list;


    private GlobalPreference globalPreference;
    private String ip,uid;
    private ImageView backIV;

    String bookingId,driverId,amount,date,accNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
        accNo = globalPreference.getAccountNo();

        iniit();

        bookingId = getIntent().getStringExtra("bookingId");
        amount = getIntent().getStringExtra("amount");
        driverId = getIntent().getStringExtra("driverId");
        date = getIntent().getStringExtra("date");

        Toast.makeText(this, ""+amount, Toast.LENGTH_SHORT).show();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentActivity.this,UserHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        amountTV.setText("₹ "+amount);

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());

            Date datee = inputFormat.parse(date);
            String formattedDate = outputFormat.format(datee);

            dateTV.setText(formattedDate);

            System.out.println(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        accountsRV.setLayoutManager(layoutManager);

        makePaymentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "accountNoo1: "+globalPreference.getAccountNo());

                accNo = globalPreference.getAccountNo();

                if (accNo.equals("")){
                    Toast.makeText(PaymentActivity.this, "Select Account", Toast.LENGTH_SHORT).show();
                }else {

                   payNow();
                }


            }
        });

        getAccounts();
    }



    private void payNow() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/payment.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("accerror")){

                    Toast.makeText(PaymentActivity.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
                    intent.putExtra("bookingId",bookingId);
                    intent.putExtra("driverId",driverId);
                    intent.putExtra("amount",amount);
                    intent.putExtra("date",date);
                    startActivity(intent);
                    finish();

                }
                else if (response.equals("success")){

                    showAlert();
                }
                else{
                    Toast.makeText(PaymentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(PaymentActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            @Nullable
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId",uid);
                params.put("driverId",driverId);
                params.put("bookingId",bookingId);
                params.put("amount",amount);
                params.put("accNumber",accNo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showAlert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(" Payment Success");
        alertDialogBuilder.setIcon(R.drawable.check);
        alertDialogBuilder.setMessage("The amount has been Credited to the Driver's Account");
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //on Success this action takes place

                Intent intent = new Intent(PaymentActivity.this,UserDashboardActivity.class);
                startActivity(intent);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    private void getAccounts() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/eriksha/api/getAccounts.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(PaymentActivity.this, "No Accounts Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String accname = object.getString("accname");
                            String accountno = object.getString("accountno");
                            String bank = object.getString("bank");
                            String balance = object.getString("amount");

                            list.add(new MyAccountModelClass(id,accname,accountno,bank,balance));

                        }

                        MyAccountAdapter adapter = new MyAccountAdapter(list,PaymentActivity.this);
                        accountsRV.setAdapter(adapter);

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

    private void iniit() {

        amountTV = findViewById(R.id.cAmountTextView);
        dateTV = findViewById(R.id.cDateTextView);
        makePaymentBT = findViewById(R.id.makePaymentButton);
        backIV = findViewById(R.id.cBackImageVIew);
        accountsRV = findViewById(R.id.myAccountsRecyclerView);
    }
}