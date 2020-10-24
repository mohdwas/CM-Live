package com.e.cmlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.cmlive.R;
import com.e.cmlive.interfaces.CashOutListener;

public class CashOutAdapter extends RecyclerView.Adapter<CashOutAdapter.CashOutViewHolder> {

    private Context context;
    private CashOutListener listener;

    public CashOutAdapter(Context context, CashOutListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CashOutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CashOutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cash_out, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CashOutViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            listener.onPackSelect();
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CashOutViewHolder extends RecyclerView.ViewHolder {
        public CashOutViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
