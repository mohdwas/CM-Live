package com.e.cmlive.viewModel;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cmlive.models.CheckPhoneModel;
import com.e.cmlive.repository.AuthRepository;

public class LoginPhoneViewModel  extends ViewModel {

    public MutableLiveData<String> clickLiveData = new MutableLiveData<>();
    //public ObservableField<String> phone = new ObservableField<>("");
    public String phone = "";
    public AuthRepository authRepository;
    private LiveData<CheckPhoneModel>  checkPhoneModelLiveData;

    public LoginPhoneViewModel() {
        authRepository = new AuthRepository();
    }

    public void nextClick(View view) {
        if(phone.isEmpty()) {
            clickLiveData.postValue("fill_fields");
            return;
        }

        this.checkPhoneModelLiveData = authRepository.checkPhoneNumber(phone);
        clickLiveData.postValue("next");

    }

    public LiveData<CheckPhoneModel> checkPhoneNumber() {
        return checkPhoneModelLiveData;
    }

    public void onGoogleLoginClick(View view) {
        clickLiveData.postValue("google");
    }

    public void backClick(View view) {
        clickLiveData.postValue("back");
    }
}
