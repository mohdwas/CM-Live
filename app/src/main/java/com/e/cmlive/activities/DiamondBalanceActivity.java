package com.e.cmlive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.e.cmlive.R;
import com.e.cmlive.utils.IntentUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiamondBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diamond_balance);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.convert_coin, R.id.cash_out})
    void OnItemClick(View view) {
        switch (view.getId()) {
            case R.id.convert_coin: {
                IntentUtils.startIntent(this, ConvertCoinsActivity.class);
                break;
            }
            case R.id.cash_out: {
                IntentUtils.startIntent(this, CashOutAgreementActivity.class);
                break;
            }
        }
    }
}