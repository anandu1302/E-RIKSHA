package com.nextgen.eriksha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IPActivity extends AppCompatActivity {

    private GlobalPreference globalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipactivity);

        globalPreference = new GlobalPreference(this);

        getPermissions();

        getIP();
    }

    private void getIP() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View mview = layoutInflater.inflate(R.layout.user_ip_box,null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mview);
        final EditText userInputDialogEditText = mview.findViewById(R.id.ipaddressEditText);
        userInputDialogEditText.setText(globalPreference.getIP());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogbox, int id) {

                        globalPreference.saveIP(userInputDialogEditText.getText().toString());

                        userInputDialogEditText.setText(userInputDialogEditText.getText().toString());

                        Intent mintent = new Intent(getApplicationContext(),SplashActivity.class);
                        startActivity(mintent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogbox, int id) {
                        dialogbox.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }

    private void getPermissions() {
        //Requesting Permissions For App
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // Permission has not been granted, therefore prompt the user to grant permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.SEND_SMS, android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 10);
            }
        }
    }
}