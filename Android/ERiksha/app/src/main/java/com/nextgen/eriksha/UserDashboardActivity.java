package com.nextgen.eriksha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserDashboardActivity extends AppCompatActivity {

    TextView usernameTV;
    ImageView logoutIV;
    CardView myBookingsCV;
    CardView accountDetailsCV;

    CardView feedbackCV;

    private GlobalPreference globalPreference;
    private String ip,uid,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
        name = globalPreference.getName();

        usernameTV = findViewById(R.id.userNameeTextView);
        logoutIV = findViewById(R.id.userLogoutIV);
        myBookingsCV = findViewById(R.id.card_myBookings);
        accountDetailsCV = findViewById(R.id.card_account);
        feedbackCV = findViewById(R.id.card_feedback);


        myBookingsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDashboardActivity.this,MyBookingsActivity.class);
                startActivity(intent);
            }
        });


        feedbackCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDashboardActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });

        accountDetailsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDashboardActivity.this,AccountDetailsActivity.class);
                startActivity(intent);
            }
        });

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();
            }
        });


        usernameTV.setText(name);
    }

    private void logout() {

        new AlertDialog.Builder(UserDashboardActivity.this)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(UserDashboardActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}