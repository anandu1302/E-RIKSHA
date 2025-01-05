package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailET;
    Button sendBT;
    TextView loginTV;


    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();


        emailET = findViewById(R.id.emailET);
        sendBT = findViewById(R.id.sendButton);
        loginTV = findViewById(R.id.loginTextView);



        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForgotPasswordActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });



    }

    private void sendEmail() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/eriksha/api/forgotPassword.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("failed")){
                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send Email", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(ForgotPasswordActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgotPasswordActivity.this,UserLoginActivity.class);
                    startActivity(intent);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPasswordActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",emailET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        requestQueue.add(stringRequest);
    }
}