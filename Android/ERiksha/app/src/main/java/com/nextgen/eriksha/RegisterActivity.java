package com.nextgen.eriksha;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class RegisterActivity extends AppCompatActivity {

    EditText nameET;
    EditText emailET;
    EditText numberET;
    EditText usernameET;
    EditText passwordET;
    Button registerBT;
    TextView signInTV;

    private GlobalPreference globalPreference;
    private String ip;
    private boolean val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        nameET = findViewById(R.id.nameEditText);
        emailET = findViewById(R.id.emailEditText);
        numberET = findViewById(R.id.numberEditText);
        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        registerBT = findViewById(R.id.registerButton);
        signInTV = findViewById(R.id.signInTextView);

        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check();

            }
        });

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void check() {

        if (nameET.getText().toString().equals("")) {
            nameET.setError("Please Enter name");
        }  else if (emailET.getText().equals("")) {
            emailET.setError("Please Enter Email");
        }else if (numberET.getText().equals("") || numberET.getText().length() > 10 || numberET.getText().length() < 10) {
            numberET.setError("Invalid Phone number ");
        }
        if (usernameET.getText().toString().equals("")) {
            usernameET.setError("Please Enter username");
        } else if (passwordET.getText().equals("") || passwordET.getText().length() < 5) {
            passwordET.setError("Password Empty or It Does not contain 5 letters");
        } else if (emailET.getText().length() > 0) {
            val = validateEmail(emailET);
            if (val == true) {
                register();
            } else {
                Toast.makeText(RegisterActivity.this, "Please Check Your Email id", Toast.LENGTH_LONG).show();
            }
        }



    }

    private void register() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/eriksha/api/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
                    Intent intent = new Intent(RegisterActivity.this,UserLoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            @Nullable
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",nameET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("username",usernameET.getText().toString());
                params.put("password",passwordET.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    private boolean validateEmail(EditText emailET) {
        String email = emailET.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Toast.makeText(UserRegisterActivity.this,"Email Validated",Toast.LENGTH_SHORT).show();
            return true;
        } else {
            //  Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            return false;

        }
    }
}