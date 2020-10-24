package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;
import com.e.cmlive.interfaces.FeatureListener;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    private Context context;
    private FeatureListener listener;

    public FeaturedAdapter(Context context, FeatureListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeaturedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            listener.onFeatureSelect();
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class FeaturedViewHolder extends RecyclerView.ViewHolder {
        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
