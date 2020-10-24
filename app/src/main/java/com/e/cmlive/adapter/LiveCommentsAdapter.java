package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class LiveCommentsAdapter extends RecyclerView.Adapter<LiveCommentsAdapter.LiveCommentsViewHolder> {

    private Context context;

    public LiveCommentsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LiveCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LiveCommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LiveCommentsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class LiveCommentsViewHolder extends RecyclerView.ViewHolder {

        public LiveCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
