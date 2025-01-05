package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.BookingsModelClass;
import com.nextgen.eriksha.PaymentActivity;
import com.nextgen.eriksha.R;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder>{

    private String TAG ="BookingsAdapter";

    ArrayList<BookingsModelClass> list;
    Context context;
    String ip,uid;

    public BookingsAdapter(ArrayList<BookingsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_my_bookings,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BookingsModelClass bookingList = list.get(position);
        holder.routeTV.setText(bookingList.getStart()+" to "+bookingList.getDestination());
        holder.dateTV.setText(bookingList.getDate());

        if (bookingList.getStatus().equals("waiting")){

            holder.statusTV.setText(bookingList.getStatus());
            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.grey));

            holder.amountTV.setVisibility(View.INVISIBLE);
            holder.driverNameTV.setVisibility(View.INVISIBLE);
            holder.paynowTV.setVisibility(View.GONE);

        }else if (bookingList.getStatus().equals("accepted")) {

            holder.statusTV.setText(bookingList.getStatus());
            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.green));

            holder.driverNameTV.setText(bookingList.getDriverName());
            holder.amountTV.setVisibility(View.INVISIBLE);
            holder.paynowTV.setVisibility(View.GONE);



        }
        else if (bookingList.getStatus().equals("onTrip")) {

            holder.statusTV.setText(bookingList.getStatus());
            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.dyellow));

            holder.driverNameTV.setText(bookingList.getDriverName());
            holder.amountTV.setVisibility(View.INVISIBLE);
            holder.paynowTV.setVisibility(View.GONE);



        } else if (bookingList.getStatus().equals("paid")) {

            holder.statusTV.setText(bookingList.getStatus());
            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.red));

            holder.driverNameTV.setText(bookingList.getDriverName());
            holder.amountTV.setText("₹ "+bookingList.getAmount());
            holder.paynowTV.setVisibility(View.GONE);


        } else{
          holder.statusTV.setText(bookingList.getStatus());
         // holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.green));

          holder.amountTV.setText("₹ "+bookingList.getAmount());
          holder.driverNameTV.setText(bookingList.getDriverName());

          holder.paynowTV.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  Intent intent = new Intent(context, PaymentActivity.class);
                  intent.putExtra("bookingId",bookingList.getId());
                  intent.putExtra("driverId",bookingList.getDriverId());
                  intent.putExtra("amount",bookingList.getAmount());
                  intent.putExtra("date",bookingList.getDate());
                  context.startActivity(intent);
              }
          });

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView routeTV;
        TextView dateTV;
        TextView driverNameTV;
        TextView amountTV;
        TextView statusTV;
        TextView paynowTV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            routeTV = itemview.findViewById(R.id.bRouteTextView);
            dateTV = itemview.findViewById(R.id.bDateTextView);
            driverNameTV = itemview.findViewById(R.id.bDriverNameTextView);
            amountTV = itemview.findViewById(R.id.bPriceTextView);
            statusTV = itemview.findViewById(R.id.bStatusTextView);
            paynowTV = itemview.findViewById(R.id.bPayNowTextView);


        }
    }
}
