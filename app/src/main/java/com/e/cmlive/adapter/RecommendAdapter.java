package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private Context context;

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
