package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.GlobalPreference;
import com.nextgen.eriksha.ModelClass.FeedbackModelClass;
import com.nextgen.eriksha.R;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder>{

    ArrayList<FeedbackModelClass> list;
    Context context;
    String ip;
    String id;

    private GlobalPreference globalPreference;

    public FeedbackAdapter(ArrayList<FeedbackModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        id = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_feedbacks, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FeedbackModelClass feedbackList = list.get(position);
        holder.usernameTV.setText(feedbackList.getName());
        holder.feedbackRB.setRating(Float.valueOf(feedbackList.getRating()));
        holder.feedbackTV.setText(feedbackList.getFeedback());

        String iconText = feedbackList.getName();
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

        TextView userIconTV;
        TextView usernameTV;
        TextView feedbackTV;
        RatingBar feedbackRB;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userIconTV = itemView.findViewById(R.id.feedBackIconTextView);
            usernameTV = itemView.findViewById(R.id.feedbackUserNameTextView);
            feedbackTV = itemView.findViewById(R.id.feedbackTV);
            feedbackRB = itemView.findViewById(R.id.feedbackRatingBar);


        }
    }

}
