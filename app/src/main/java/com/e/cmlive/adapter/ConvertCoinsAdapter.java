package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;
import com.e.cmlive.interfaces.ConvertCoinsListener;

public class ConvertCoinsAdapter extends RecyclerView.Adapter<ConvertCoinsAdapter.ConvertCoinsViewHolder> {

    private Context context;
    private ConvertCoinsListener listener;

    public ConvertCoinsAdapter(Context context, ConvertCoinsListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConvertCoinsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConvertCoinsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_convert_coins, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConvertCoinsViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            listener.onPlanSelect();
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ConvertCoinsViewHolder extends RecyclerView.ViewHolder {
        public ConvertCoinsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
