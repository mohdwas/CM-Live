package com.e.cmlive.viewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.cmlive.models.RegisterModel;
import com.e.cmlive.repository.AuthRepository;

public class AddDetailsViewModel extends ViewModel {

    public MutableLiveData<String> clickLiveData = new MutableLiveData<>();
    public String name ="";
    public String phone_number="";
    public String password="";
    public String dob="";
    public String gender="";
    public String profile_image="";
    public AuthRepository authRepository;
    private LiveData<RegisterModel> registerModelLiveData;

    public AddDetailsViewModel() {
        authRepository = new AuthRepository();
    }

    public void saveClick(View v) {
        if(name.isEmpty()) {
            clickLiveData.postValue("fill_fields");
            return;
        }

        this.registerModelLiveData = authRepository.register(name,phone_number,password,dob,gender,profile_image);
        clickLiveData.postValue("save");
    }

    public LiveData<RegisterModel> data() {
        return registerModelLiveData;
    }

}
