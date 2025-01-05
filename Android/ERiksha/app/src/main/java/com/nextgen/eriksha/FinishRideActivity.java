package com.nextgen.eriksha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FinishRideActivity extends AppCompatActivity {

    TextView nameTV;
    TextView numberTV;
    TextView amountTV;
    TextView dateTV;
    TextView doneTV;
    ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_ride);

        nameTV = findViewById(R.id.pNameTextView);
        numberTV = findViewById(R.id.pNumberTextView);
        amountTV = findViewById(R.id.pPriceTextView);
        dateTV = findViewById(R.id.pDateTextView);
        doneTV = findViewById(R.id.pDoneTextView);
        backIV = findViewById(R.id.rideBackImageView);

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            JSONObject object = jsonArray.getJSONObject(0);
            String name = object.getString("name");
            String number = object.getString("number");
            String price = object.getString("amount");
            String date_time = object.getString("trip_date");

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());

                Date date = inputFormat.parse(date_time);
                String formattedDate = outputFormat.format(date);

                dateTV.setText(formattedDate);

                System.out.println(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            nameTV.setText(name);
            numberTV.setText(number);
            amountTV.setText("₹ "+price);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        doneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishRideActivity.this, DriverMapActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}