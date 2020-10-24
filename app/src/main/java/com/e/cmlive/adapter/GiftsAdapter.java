package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.GiftsViewHolder> {

    private Context context;

    public GiftsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GiftsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GiftsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gifts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GiftsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class GiftsViewHolder extends RecyclerView.ViewHolder {
        public GiftsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
