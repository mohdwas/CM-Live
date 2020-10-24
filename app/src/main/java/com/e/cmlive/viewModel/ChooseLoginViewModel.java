package com.e.cmlive.viewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cmlive.models.LoginModel;
import com.e.cmlive.repository.AuthRepository;

public class ChooseLoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    public String name = "", email = "";
    public String location = "", time = "", deviceModel = "";
    public MutableLiveData<String> clickLiveData = new MutableLiveData<>();
    public LiveData<LoginModel> loginLiveData;

    public ChooseLoginViewModel() {
        authRepository = new AuthRepository();
    }



    public void phoneClick(View view) {
        clickLiveData.postValue("phone");
    }

    public void googleClick(View view) {
        deviceModel = getMobileDevice();
        clickLiveData.postValue("google");
    }

    public void startGoogleLogin(){
        Log.d("thisisdata", name + "," + email + "," + deviceModel + "," + time + "," + location);
        loginLiveData = authRepository.google_login("1234", name, email, deviceModel, time, location);
    }

    public LiveData<LoginModel> google_login() {
        return loginLiveData;
    }


    public String getMobileDevice() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.PRODUCT;
    }


    public void termsClick(View view) {
        clickLiveData.postValue("terms");
    }

    public void privacyClick(View view) {
        clickLiveData.postValue("privacy");
    }

    public void helpClick(View view) {
        clickLiveData.postValue("help");
    }

    public void forgotPasswordClick(View view) {
        clickLiveData.postValue("forgot");
    }
}
