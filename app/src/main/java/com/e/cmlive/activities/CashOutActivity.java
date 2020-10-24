package com.e.cmlive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.e.cmlive.R;
import com.e.cmlive.adapter.CashOutAdapter;
import com.e.cmlive.adapter.ConvertCoinsAdapter;
import com.e.cmlive.utils.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.e.cmlive.interfaces.CashOutListener;

public class CashOutActivity extends AppCompatActivity implements CashOutListener{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_out);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CashOutAdapter(this, this));
    }

    @Override
    public void onPackSelect() {
        IntentUtils.startIntent(this, TotalCashOutActivity.class);
    }
}