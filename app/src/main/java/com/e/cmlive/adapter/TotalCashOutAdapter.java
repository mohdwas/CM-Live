package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;

public class TotalCashOutAdapter extends RecyclerView.Adapter<TotalCashOutAdapter.TotalCashOutViewHolder> {

    private Context context;

    public TotalCashOutAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TotalCashOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TotalCashOutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_cash_out, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TotalCashOutViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class TotalCashOutViewHolder extends RecyclerView.ViewHolder {
        public TotalCashOutViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
