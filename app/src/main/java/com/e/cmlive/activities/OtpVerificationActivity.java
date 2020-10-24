package com.e.cmlive.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.databinding.ActivityOtpVerificationBinding;
import com.e.cmlive.models.LoginModel;
import com.e.cmlive.utils.IntentUtils;
import com.e.cmlive.viewModel.OtpVerificationViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends BaseActivity {

    private ActivityOtpVerificationBinding binding;
    private OtpVerificationViewModel viewModel;
    private Activity context;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 101;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private FusedLocationProviderClient mFusedLocationClient;
    public LatLng currentLatLng;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verification);
        viewModel = new ViewModelProvider(this).get(OtpVerificationViewModel.class);
        binding.setViewModel(viewModel);

        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        binding.mobile.setText(phone+" please enter it here");
        sendVerificationCode(phone);

        init();
        getLocation();
        viewModel.time = getCurrentDateTime(System.currentTimeMillis(), "yyyy-MM-dd hh:mm:ss");//2020-10-21 15:30:00

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        viewModel.clickLiveData.observe(this, s -> {
            switch (s) {
                case "verify": {
                    String code = binding.otp.getText().toString();
                    if (code.isEmpty() || code.length() < 6) {
                        binding.otp.setError("Enter valid code");
                        binding.otp.requestFocus();
                        return;
                    }
                    //verifying the code entered manually
                    verifyVerificationCode(code);
                    break;
                }
                case "google": {
                    signIn();
                    binding.progressbar.setVisibility(View.VISIBLE);
                    break;
                }
                case "back": {
                    finish();
                    break;
                }
            }
        });

    }

    private void init() {
        context = this;
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


    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                binding.otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(OtpVerificationActivity.this, AddDetailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            String message = "Somthing is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                        }
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                                viewModel.name = user.getDisplayName();
                                viewModel.email = user.getEmail();
                                viewModel.startGoogleLogin();
                                googleLogin();
                                updateUI(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, task.toString().toString(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);

    }

    private void googleLogin() {
        viewModel.google_login().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                if(loginModel != null) {
                    if (loginModel.isStatus()) {
                        signIn();
                    } else {
                        Toast.makeText(context, "Firstly add gmail account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "wrong phone number or  password", Toast.LENGTH_SHORT).show();
                }
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }



}
