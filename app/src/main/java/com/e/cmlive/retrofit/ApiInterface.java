package com.e.cmlive.retrofit;

import com.e.cmlive.models.CheckPhoneModel;
import com.e.cmlive.models.LoginModel;
import com.e.cmlive.models.RegisterModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> Login(
            @Field("device_token") String device_token,
            @Field("phone_number") String phone_number,
            @Field("password") String password,
            @Field("device_name") String device_name,
            @Field("log_time") String log_time,
            @Field("location") String location
    );

    @FormUrlEncoded
    @POST("google_login.php")
    Call<LoginModel> google_login(
            @Field("device_token") String device_token,
            @Field("name") String name,
            @Field("email") String email,
            @Field("device_name") String device_name,
            @Field("log_time") String log_time,
            @Field("location") String location
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterModel> Register(
            @Field("name") String name,
            @Field("phone_number") String phone_number,
            @Field("password") String password,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("profile_image") String profile_image
    );

    @FormUrlEncoded
    @POST("check_phone_number.php")
    Call<CheckPhoneModel> checkPhoneRequest(
            @Field("phone_number") String phone
    );

    @Multipart
    @POST("register.php")
    Call<RegisterModel> register(
            @Part("name") RequestBody name,
            @Part("phone_number") RequestBody phone,
            @Part("password") RequestBody password,
            @Part("dob") RequestBody dob,
            @Part("gender") RequestBody gender,
            @Part("device_token") RequestBody deviceToken,
            @Part MultipartBody.Part image
    );

}
