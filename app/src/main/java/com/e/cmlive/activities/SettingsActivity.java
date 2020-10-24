package com.e.cmlive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.e.cmlive.PrefsHelper;
import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.logout)
    TextView logout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.logout)
    public void onViewClicked() {
        prefsHelper.clearAllPref();
        auth.signOut();
        Intent intent = new Intent(getApplicationContext(),ChooseLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}