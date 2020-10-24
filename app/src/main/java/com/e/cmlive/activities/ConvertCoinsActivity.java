package com.e.cmlive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.e.cmlive.R;
import com.e.cmlive.adapter.ConvertCoinsAdapter;
import com.e.cmlive.interfaces.ConvertCoinsListener;
import com.e.cmlive.utils.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConvertCoinsActivity extends AppCompatActivity implements ConvertCoinsListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_coins);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ConvertCoinsAdapter(this, this));
    }

    @Override
    public void onPlanSelect() {
        Toast.makeText(this, "Plan buy success", Toast.LENGTH_SHORT).show();
    }

}