package com.e.cmlive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TimingLogger;

import com.e.cmlive.PrefsHelper;
import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.utils.IntentUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            if(prefsHelper.getPref(PrefsHelper.USER_ID) == null) {
                IntentUtils.startIntent(this, ChooseLoginActivity.class);
            } else {
                IntentUtils.startIntent(this, HomeActivity.class);
            }
            finish();
        }, 1000);
    }
}