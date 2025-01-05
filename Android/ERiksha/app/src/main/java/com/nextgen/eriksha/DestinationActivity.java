package com.nextgen.eriksha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nextgen.eriksha.Adapter.SearchListAdapter;
import com.nextgen.eriksha.ModelClass.SearchListModelClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DestinationActivity extends AppCompatActivity {

    EditText startET;
    EditText destinationET;
    TextView doneButtonTV;
    Double lat,longi;

    private ArrayList<SearchListModelClass> listModels;
    private RecyclerView recyclerView;
    private SearchListAdapter searchListAdapter;

    private Integer image[] = {R.drawable.home,R.drawable.timer_icon,R.drawable.timer_icon,R.drawable.timer_icon,};
    private String title[] = {"Home","Palarivattom","Edapally","Vyttila"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("latitude",0);
        longi = intent.getDoubleExtra("longitude",0);

        startET = findViewById(R.id.originEditText);
        destinationET = findViewById(R.id.destinationEditText);
        recyclerView = findViewById(R.id.rvSearchResult);
        doneButtonTV = findViewById(R.id.doneButtonTV);

        startET.setText(getAddress(lat,longi));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DestinationActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listModels = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            SearchListModelClass ab = new SearchListModelClass(image[i],title[i]);
            listModels.add(ab);
        }

        searchListAdapter = new SearchListAdapter(DestinationActivity.this, listModels);
        recyclerView.setAdapter(searchListAdapter);

        doneButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (destinationET.getText().toString().equals("")){
                    Toast.makeText(DestinationActivity.this, "Enter Destination", Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = new Intent(DestinationActivity.this, RequestBookingActivity.class);
                    intent.putExtra("latitude",lat);
                    intent.putExtra("longitude",longi);
                    intent.putExtra("destination",destinationET.getText().toString());
                    startActivity(intent);

                }
            }
        });



    }


    public String getAddress(double lat, double lng) {

        Log.v("IGA", "Address" + lat + "  " + lng);

        Geocoder geocoder = new Geocoder(DestinationActivity.this, Locale.getDefault());
        String address = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            String[] splited = add.split(",");
            address = splited[0] + ", " + splited[1] + "\n" + splited[2] + ", " + splited[3] + ", " + splited[4];

            Log.v("IGA", "Address" + add);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return address;
    }
}