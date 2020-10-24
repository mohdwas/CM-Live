package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;
import com.e.cmlive.interfaces.FilterStickerListener;

public class FilterStickerAdapter extends RecyclerView.Adapter<FilterStickerAdapter.FilterStyleViewHolder> {

    private Context context;
    private FilterStickerListener listener;

    public FilterStickerAdapter(Context context, FilterStickerListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterStyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterStyleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_sticker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterStyleViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            listener.onFilterSelect();
        });

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class FilterStyleViewHolder extends RecyclerView.ViewHolder {

        public FilterStyleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
