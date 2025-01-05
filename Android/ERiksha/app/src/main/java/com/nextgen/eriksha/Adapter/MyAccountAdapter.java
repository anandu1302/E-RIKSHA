package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.MyAccountModelClass;
import com.nextgen.eriksha.R;

import java.util.ArrayList;

public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.MyViewHolder>{

    private ArrayList<MyAccountModelClass> list;
    private Context context;
    private int selectedPosition = -1;
    private String ip;

    GlobalPreference globalPreference;

    public MyAccountAdapter(ArrayList<MyAccountModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_user_account,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyAccountModelClass account = list.get(position);

        holder.bankNameTV.setText(account.getBank());
        holder.accNumberTV.setText(account.getAccountno());
        holder.balanceTV.setText("₹ "+account.getBalance());

        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.selected_background));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.default_background));
        }

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                globalPreference.saveAccountNo(account.getId());
                Log.d("TAG", "accountNoo: "+account.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bankNameTV;
        TextView accNumberTV;
        TextView balanceTV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            bankNameTV = itemview.findViewById(R.id.mBankNameTextView);
            accNumberTV = itemview.findViewById(R.id.mAccountNumberTextView);
            balanceTV = itemview.findViewById(R.id.mBalanceTextView);
        }
    }
}