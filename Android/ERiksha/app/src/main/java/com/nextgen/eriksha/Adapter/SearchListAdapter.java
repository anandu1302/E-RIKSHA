package com.nextgen.eriksha.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.eriksha.DestinationActivity;
import com.nextgen.eriksha.ModelClass.SearchListModelClass;
import com.nextgen.eriksha.R;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder>{

    Context context;
    private ArrayList<SearchListModelClass> searchResultModels;

    public SearchListAdapter(Context context, ArrayList<SearchListModelClass> searchResultModels) {
        this.context = context;
        this.searchResultModels = searchResultModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SearchListModelClass searchList = searchResultModels.get(position);
        holder.title.setText(searchList.getTitle());
        holder.imageView.setImageResource(searchList.getImage());

    }

    @Override
    public int getItemCount() {
        return searchResultModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.tvTitle);
            imageView = view.findViewById(R.id.ivMain);
        }
    }
}
