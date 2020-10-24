package com.e.cmlive.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.databinding.ActivityLoginPasswordBinding;
import com.e.cmlive.models.LoginModel;
import com.e.cmlive.utils.IntentUtils;
import com.e.cmlive.viewModel.AddDetailsViewModel;
import com.e.cmlive.viewModel.LoginPasswordViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class LoginPasswordActivity extends BaseActivity {

    private ActivityLoginPasswordBinding binding;
    private LoginPasswordViewModel viewModel;
    private Activity context;
    private FusedLocationProviderClient mFusedLocationClient;
    public LatLng currentLatLng;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_password);
        viewModel = new ViewModelProvider(this).get(LoginPasswordViewModel.class);
        binding.setViewModel(viewModel);

        init();
        getLocation();
        viewModel.time = getCurrentDateTime(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss");//2020-10-21 15:30:00

        viewModel.clickLiveData.observe(this, s -> {
            switch (s) {
                case "back": {
                    finish();
                    break;
                }
                case "login": {
                    login();
                    binding.progressbar.setVisibility(View.VISIBLE);
                    break;
                }
                case "forgot": {

                    break;
                }
            }
        });


    }

    private void init() {
        context = this;
        viewModel.phone = getIntent().getStringExtra("phone");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void getLocation() {
        askLocationPermission();
        if(checkLocationPermission()) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    double wayLatitude = location.getLatitude();
                    double wayLongitude = location.getLongitude();
                    currentLatLng = new LatLng(wayLatitude, wayLongitude);
                    address = getAddressFromLatLong(wayLatitude, wayLongitude);
                    viewModel.location = address;
                }
            });
        }
    }

    private void login() {
        viewModel.login().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                if(loginModel != null) {
                    if (loginModel.isStatus()) {
                        IntentUtils.startIntentFromStart(context, HomeActivity.class);
                    } else {
                        Toast.makeText(context, "wrong password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "wrong phone number or  password", Toast.LENGTH_SHORT).show();
                }
                binding.progressbar.setVisibility(View.GONE);


            }
        });
    }
}
