package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.LikesViewHolder> {

    private Context context;

    public LikesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LikesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class LikesViewHolder extends RecyclerView.ViewHolder {

        public LikesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
