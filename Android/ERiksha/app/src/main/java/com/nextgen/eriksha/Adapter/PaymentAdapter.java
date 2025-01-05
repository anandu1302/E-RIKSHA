package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.PaymentModelClass;
import com.nextgen.eriksha.R;

import java.util.ArrayList;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder>{

    ArrayList<PaymentModelClass> list;
    Context context;
    String ip;

    public PaymentAdapter(ArrayList<PaymentModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_payments,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PaymentModelClass paymentList = list.get(position);
        holder.amountTV.setText("₹ "+paymentList.getAmount());
        holder.dateTV.setText(paymentList.getDate());

        holder.userNameTV.setText(paymentList.getUserName());
        holder.userNumberTV.setText(paymentList.getUserNumber());

        String number = paymentList.getCardnumber();
        String cardno = number.replaceAll("\\w(?=\\w{4})", "*");
        holder.cardNumberTV.setText(cardno);

        String iconText = paymentList.getUserName();
        char first = iconText.charAt(0);
        String firstLetter = String.valueOf(first);

        //setting the first letter of user name as icon
        holder.userIconTV.setText(firstLetter.toUpperCase());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView amountTV;
        TextView dateTV;
        TextView cardNumberTV;
        TextView userNameTV;
        TextView userNumberTV;
        TextView userIconTV;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            amountTV = itemview.findViewById(R.id.pAmountTextView);
            dateTV = itemview.findViewById(R.id.pDateTextView);
            cardNumberTV = itemview.findViewById(R.id.pCardNumberTextView);
            userNameTV = itemview.findViewById(R.id.pUserNameTextView);
            userNumberTV = itemview.findViewById(R.id.pUserNumberTextView);
            userIconTV = itemView.findViewById(R.id.paymentIconTextView);
        }
    }
}
