package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ActiveViewHolder> {

    private Context context;

    public ActiveAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveAdapter.ActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAdapter.ActiveViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ActiveViewHolder extends RecyclerView.ViewHolder {

        public ActiveViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
