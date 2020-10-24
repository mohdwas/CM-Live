package com.e.cmlive.activities;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.e.cmlive.PrefsHelper;
import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.databinding.ActivityAddDetailsBinding;
import com.e.cmlive.models.RegisterModel;
import com.e.cmlive.retrofit.ApiInterface;
import com.e.cmlive.retrofit.RetrofitClient;
import com.e.cmlive.utils.IntentUtils;
import com.e.cmlive.utils.Utils;
import com.e.cmlive.viewModel.AddDetailsViewModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.e.cmlive.Constants.BASE_URL;
import static com.e.cmlive.Constants.CAMERA_REQUEST_CODE;
import static com.e.cmlive.Constants.GALLERY_REQUEST_CODE;

public class AddDetailsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private ActivityAddDetailsBinding binding;
    private AddDetailsViewModel viewModel;
    private Activity context;
    private Uri cameraUri, selectedImageUri;
    private File file;
    private ArrayList<String> arrayList;
    private String item;
    private int year, day, month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_details);
        viewModel = new ViewModelProvider(this).get(AddDetailsViewModel.class);
        binding.setViewModel(viewModel);

        context = this;



        viewModel.clickLiveData.observe(this, s -> {
            switch (s) {
                case "save": {
                    sendRegisterRequestByRetrofit();
                    break;
                }
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add("Select");
        arrayList.add("Male");
        arrayList.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(dataAdapter);
        binding.spinner.setOnItemSelectedListener(this);


        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalender();
            }
        });
    }

    public void openCalender(){
        Calendar mcurrentDate=Calendar.getInstance();
        year=mcurrentDate.get(Calendar.YEAR);
        month=mcurrentDate.get(Calendar.MONTH);
        day=mcurrentDate.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog   mDatePicker =new DatePickerDialog(AddDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                binding.dob.setText(new StringBuilder().append(selectedyear).append("-").append(selectedmonth).append("-").append(selectedday));
                viewModel.dob = selectedyear+"-"+selectedmonth+"-"+selectedday;
            }
        },year, month, day);

        mDatePicker.setTitle("Please select date");
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();

    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDetailsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    askCameraPermission();
                    askReadWritePermission();
                    if (!checkCameraPermission()) {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "Image for CM Live");
                        cameraUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                        startActivityForResult(intent, CAMERA_REQUEST_CODE);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    askReadWritePermission();
                    if (!checkReadWritePermission()) {
                        IntentUtils.startGalleryIntent(context);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(cameraUri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(cameraUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        String filePath = Utils.getPath(this, cameraUri);

                        Bitmap b = Utils.rotateImage1(bitmap, filePath);
                        file = new File(cameraUri.getPath());

                        Glide.with(context).load(b).into(binding.image1);
                        Glide.with(context).load(b).into(binding.image2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            selectedImageUri = data.getData();
            if(selectedImageUri != null) {
                file = new File(selectedImageUri.getPath());

                Glide.with(context).load(selectedImageUri).into(binding.image1);
                Glide.with(context).load(selectedImageUri).into(binding.image2);
            }
        }
    }

    private void sendRegisterRequestByRetrofit() {
        binding.progressbar.setVisibility(View.VISIBLE);
        MultipartBody.Part body = null;

        if(file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), viewModel.name);
        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), viewModel.phone_number);
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), viewModel.password);
        RequestBody dob = RequestBody.create(MediaType.parse("multipart/form-data"), viewModel.dob);
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), viewModel.gender);
        RequestBody device_token = RequestBody.create(MediaType.parse("multipart/form-data"), "1234");

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<RegisterModel> call = apiInterface .register(name, phone, password, dob, gender, device_token, body);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.body() != null) {
                    if(response.body().isStatus()) {

                        RegisterModel model = response.body();

                        prefsHelper.savePref(PrefsHelper.USER_ID, model.getUser_detail().getUser_id());
                        prefsHelper.savePref(PrefsHelper.NAME, model.getUser_detail().getName());
                        prefsHelper.savePref(PrefsHelper.EMAIL, model.getUser_detail().getEmail());
                        prefsHelper.savePref(PrefsHelper.PHONE_NUMBER, model.getUser_detail().getPhone_number());
                        prefsHelper.savePref(PrefsHelper.DOB, model.getUser_detail().getDob());
                        prefsHelper.savePref(PrefsHelper.GENDER, model.getUser_detail().getGender());
                        prefsHelper.savePref(PrefsHelper.STATUS, model.getUser_detail().getStatus());
                        prefsHelper.savePref(PrefsHelper.PROFILE_IMAGE, model.getUser_detail().getProfile_image());

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, response.body().getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                }
                binding.progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                Log.d("thisiserror", t+"");
                binding.progressbar.setVisibility(View.GONE);
            }
        });

    }

    private void sendRegisterRequest() {

        binding.progressbar.setVisibility(View.VISIBLE);

        AndroidNetworking.upload(BASE_URL + "register.php")
                .addMultipartParameter("name", viewModel.name)
                .addMultipartParameter("phone_number", viewModel.phone_number)
                .addMultipartParameter("password", viewModel.password)
                .addMultipartParameter("dob", viewModel.dob)
                .addMultipartParameter("gender", viewModel.gender)
                .addMultipartParameter("device_token", "1234")
                .addMultipartFile("profile_image", file)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.progressbar.setVisibility(View.GONE);

                        Gson gson = new Gson();
                        RegisterModel model = gson.fromJson(String.valueOf(response), RegisterModel.class);


                    }
                    @Override
                    public void onError(ANError error) {
                        binding.progressbar.setVisibility(View.GONE);
                        Log.d("thisiserror", error+"");
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        viewModel.gender = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}