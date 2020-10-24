package com.e.cmlive.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.databinding.ActivityChooseLoginBinding;
import com.e.cmlive.models.LoginModel;
import com.e.cmlive.utils.IntentUtils;
import com.e.cmlive.viewModel.ChooseLoginViewModel;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ChooseLoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 101;
    private ActivityChooseLoginBinding binding;
    private ChooseLoginViewModel viewModel;
    private Activity context;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;
    public LatLng currentLatLng;
    private String address;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_login);
        viewModel = new ViewModelProvider(this).get(ChooseLoginViewModel.class);
        binding.setViewModel(viewModel);

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
                case "phone": {
                    IntentUtils.startIntent(context, LoginPhoneActivity.class);
                    break;
                }
                case "google": {
                    binding.progressbar.setVisibility(View.VISIBLE);
                    signIn();
                    break;
                }
            }
        });
    }

    private void init() {
        context = this;
        auth = FirebaseAuth.getInstance();
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
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
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