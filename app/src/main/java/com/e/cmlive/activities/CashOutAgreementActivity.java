package com.e.cmlive.activities;

import android.os.Bundle;
import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.utils.IntentUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.View;

public class CashOutAgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_out_agreement);
        ButterKnife.bind(this);

        setToolbarTitle("Agreement");
    }

    @OnClick({R.id.agree})
    public void OnItemClick(View view) {
        switch (view.getId()) {
            case R.id.agree: {
                IntentUtils.startIntent(this, CashOutActivity.class);
                break;
            }
        }
    }
}