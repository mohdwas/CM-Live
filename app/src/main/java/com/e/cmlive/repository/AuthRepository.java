package com.e.cmlive.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.e.cmlive.PrefsHelper;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.models.CheckPhoneModel;
import com.e.cmlive.models.LoginModel;
import com.e.cmlive.models.RegisterModel;
import com.e.cmlive.retrofit.ApiInterface;
import com.e.cmlive.retrofit.RetrofitClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository extends BaseActivity {
    private ApiInterface apiInterface;

    public AuthRepository() {
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
    }

    public LiveData<CheckPhoneModel> checkPhoneNumber(String phone) {
        MutableLiveData<CheckPhoneModel> data = new MutableLiveData<>();

        apiInterface.checkPhoneRequest(phone).enqueue(new Callback<CheckPhoneModel>() {
            @Override
            public void onResponse(Call<CheckPhoneModel> call, Response<CheckPhoneModel> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<CheckPhoneModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<LoginModel> login(String deviceToke, String phone, String password, String deviceName, String time, String location) {

        MutableLiveData<LoginModel> data = new MutableLiveData<>();

        apiInterface.Login(deviceToke, phone, password, deviceName, time, location).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.body() != null) {
                    data.setValue(response.body());
                    Gson gson = new Gson();
                    LoginModel loginModel = gson.fromJson(String.valueOf(response),LoginModel.class);
                    prefsHelper.savePref(PrefsHelper.USER_ID,loginModel.getUser_detail().getUser_id());
                    prefsHelper.savePref(PrefsHelper.NAME, loginModel.getUser_detail().getName());
                    prefsHelper.savePref(PrefsHelper.EMAIL, loginModel.getUser_detail().getEmail());
                    prefsHelper.savePref(PrefsHelper.PHONE_NUMBER, loginModel.getUser_detail().getPhone_number());
                    prefsHelper.savePref(PrefsHelper.DOB, loginModel.getUser_detail().getDob());
                    prefsHelper.savePref(PrefsHelper.GENDER, loginModel.getUser_detail().getGender());
                    prefsHelper.savePref(PrefsHelper.STATUS, loginModel.getUser_detail().getStatus());
                    prefsHelper.savePref(PrefsHelper.PROFILE_IMAGE, loginModel.getUser_detail().getProfile_image());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<LoginModel> google_login(String deviceToke, String name, String email, String deviceName, String time, String location ){
        MutableLiveData<LoginModel> data = new MutableLiveData<>();
        apiInterface.google_login(deviceToke,name,email,deviceName,time,location).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.body() != null){
                    data.setValue(response.body());

                }else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });
        return data;
    }
    public LiveData<RegisterModel> register(String name,String phone_number,String password,String dob,String gender,String profile_image){
        MutableLiveData<RegisterModel> liveData = new MutableLiveData<>();
        apiInterface.Register(name,phone_number,password,dob,gender,profile_image).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.body() != null){
                    liveData.setValue(response.body());
                }else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return  liveData;
    }


}
