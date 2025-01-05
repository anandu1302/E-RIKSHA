package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class AddAccountActivity extends AppCompatActivity {

    EditText accNameET;
    EditText accNumberET;
    EditText bankET;
    EditText pinET;
    EditText amountET;
    Button submitDetailsBT;

    private ImageView BackIV;
    private TextView appBarTV;

    private GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        accNameET = findViewById(R.id.accNameEditText);
        accNumberET = findViewById(R.id.accNumberEditText);
        bankET = findViewById(R.id.accBankEditText);
        pinET = findViewById(R.id.accPinEditText);
        amountET = findViewById(R.id.accAmountEditText);
        submitDetailsBT = findViewById(R.id.submitDetailsButton);
        BackIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);
        appBarTV.setText("Add Account");

        BackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddAccountActivity.this,AccountDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submitDetailsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check();
            }
        });
    }

    private void check() {

        if (accNameET.getText().toString().equals("")) {
            accNameET.setError("Please Enter name");
        }  else if (accNumberET.getText().equals("") || accNumberET.getText().length() > 12 || accNumberET.getText().length() < 12) {
            accNumberET.setError("Invalid Account number ");
        }
        else if (bankET.getText().toString().equals("")) {
            bankET.setError("Please Enter Bank Name");
        } else if (pinET.getText().equals("") || pinET.getText().length() < 5) {
            pinET.setError("Pin is Empty or It Does not contain 5 numbers");
        }else if (amountET.getText().equals("") ) {
            amountET.setError("Please Enter Amount");
        }else {
            addAccount();
        }


    }

    private void addAccount() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/addAccount.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
                    Intent intent = new Intent(AddAccountActivity.this,AccountDetailsActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddAccountActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddAccountActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("accName",accNameET.getText().toString());
                params.put("accNumber",accNumberET.getText().toString());
                params.put("bank",bankET.getText().toString());
                params.put("pin",pinET.getText().toString());
                params.put("amount",amountET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddAccountActivity.this);
        requestQueue.add(stringRequest);

    }
}