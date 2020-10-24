package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {

    private Context context;

    public FollowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FollowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
