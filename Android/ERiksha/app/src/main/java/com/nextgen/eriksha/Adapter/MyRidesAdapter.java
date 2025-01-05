package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.MyRidesModelClass;
import com.nextgen.eriksha.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyRidesAdapter extends RecyclerView.Adapter<MyRidesAdapter.MyViewHolder>{

    private String TAG ="MyRidesAdapter";

    ArrayList<MyRidesModelClass> list;
    Context context;
    String ip,uid;

    public MyRidesAdapter(ArrayList<MyRidesModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_my_rides,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyRidesModelClass rideList = list.get(position);
        holder.nameTV.setText(rideList.getName());
        holder.numberTV.setText(rideList.getNumber());
        holder.startTV.setText(rideList.getStart());
        holder.destinationTV.setText(rideList.getDestination());
        holder.statusTV.setText(rideList.getStatus());

        if (rideList.getStatus().equals("waiting") || rideList.getStatus().equals("onTrip")){

            holder.tripAmountLL.setVisibility(View.GONE);

        }else if (rideList.getStatus().equals("completed")){

            holder.tripAmountLL.setVisibility(View.VISIBLE);
            holder.rideAmountTV.setText("₹ "+rideList.getAmount());

            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.green));

        }else if (rideList.getStatus().equals("paid")){

            holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tripAmountLL.setVisibility(View.VISIBLE);
            holder.rideAmountTV.setText("₹ "+rideList.getAmount());
        }
        else {
            holder.tripAmountLL.setVisibility(View.VISIBLE);
            holder.rideAmountTV.setText("₹ "+rideList.getAmount());
        }


        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());

            Date date = inputFormat.parse(rideList.getDate());
            String formattedDate = outputFormat.format(date);
            holder.rideDateTV.setText(formattedDate);

            System.out.println(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView statusTV;
        TextView startTV;
        TextView destinationTV;
        TextView userIconTV;
        TextView rideDateTV;
        TextView rideAmountTV;
        TextView nameTV;
        TextView numberTV;
        LinearLayout tripAmountLL;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            statusTV = itemview.findViewById(R.id.rideStatusTextView);
            startTV = itemview.findViewById(R.id.rideOriginTextView);
            destinationTV = itemview.findViewById(R.id.rideDestinationTextView);
            nameTV = itemview.findViewById(R.id.rideUserNameTextView);
            rideDateTV = itemview.findViewById(R.id.rideDateTextView);
            numberTV = itemview.findViewById(R.id.rideUserNumberTextView);
            userIconTV = itemview.findViewById(R.id.rideIconTextView);
            rideAmountTV = itemview.findViewById(R.id.rideAmountTextView);
            tripAmountLL = itemview.findViewById(R.id.tripAmountLL);


        }
    }
}
