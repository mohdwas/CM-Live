package com.e.cmlive.viewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cmlive.models.LoginModel;
import com.e.cmlive.repository.AuthRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginPasswordViewModel extends ViewModel {

    private AuthRepository authRepository;
    public String phone = "", password = "";
    public String location = "", time = "", deviceModel = "";
    public MutableLiveData<String> clickLiveData = new MutableLiveData<>();
    public LiveData<LoginModel> loginLiveData;

    public LoginPasswordViewModel() {
        authRepository = new AuthRepository();
    }

    public void backClick(View view) {
        clickLiveData.postValue("back");
    }

    public void loginClick(View view) {
        deviceModel = getMobileDevice();
        Log.d("thisisdata", phone + "," + password + "," + deviceModel + "," + time + "," + location);
        loginLiveData = authRepository.login("1234", phone, password, deviceModel, time, location);
        clickLiveData.postValue("login");
    }

    public LiveData<LoginModel> login() {
        return loginLiveData;
    }

    public String getMobileDevice() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.PRODUCT;
    }

    public void codeLoginClick(View view) {
        clickLiveData.postValue("code");
    }

    public void forgotPasswordClick(View view) {
        clickLiveData.postValue("forgot");
    }
}
