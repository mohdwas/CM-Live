package com.e.cmlive.viewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cmlive.models.LoginModel;
import com.e.cmlive.repository.AuthRepository;

public class OtpVerificationViewModel extends ViewModel {
    private AuthRepository authRepository;
    public String name = "", email = "";
    public String location = "", time = "", deviceModel = "";
    public MutableLiveData<String> clickLiveData = new MutableLiveData<>();
    public LiveData<LoginModel> loginLiveData;

    public OtpVerificationViewModel() {
        authRepository = new AuthRepository();
    }


    public void verifyClick(View view) {
        clickLiveData.postValue("verify");
    }

    public void backClick(View view) {
        clickLiveData.postValue("back");
    }

    public LiveData<LoginModel> google_login() {
        return loginLiveData;
    }


    public void onGoogleLoginClick(View view) {
        deviceModel = getMobileDevice();
        clickLiveData.postValue("google");
    }

    public void startGoogleLogin(){
        Log.d("thisisdata", name + "," + email + "," + deviceModel + "," + time + "," + location);
        loginLiveData = authRepository.google_login("1234", name, email, deviceModel, time, location);
    }


    public String getMobileDevice() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.PRODUCT;
    }

}
