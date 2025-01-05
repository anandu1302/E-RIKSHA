package com.nextgen.eriksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nextgen.eriksha.Service.LocationService;

public class MainActivity extends AppCompatActivity {

    TextView userTV;
    TextView driverTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userTV = findViewById(R.id.userTextView);
        driverTV = findViewById(R.id.driverTextView);

        userTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

        driverTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,DriverLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}