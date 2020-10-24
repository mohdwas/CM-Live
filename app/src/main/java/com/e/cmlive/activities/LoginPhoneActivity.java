package com.e.cmlive.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.databinding.ActivityLoginPhoneBinding;
import com.e.cmlive.models.CheckPhoneModel;
import com.e.cmlive.utils.IntentUtils;
import com.e.cmlive.viewModel.LoginPasswordViewModel;
import com.e.cmlive.viewModel.LoginPhoneViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPhoneActivity extends BaseActivity {

    private ActivityLoginPhoneBinding binding;
    private LoginPhoneViewModel viewModel;
    private Activity context;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_phone);
        viewModel = new ViewModelProvider(this).get(LoginPhoneViewModel.class);
        binding.setViewModel(viewModel);

        context = this;

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        viewModel.clickLiveData.observe(this, s -> {
            switch (s) {
                case "next": {
                    checkPhone();
                    binding.progressbar.setVisibility(View.VISIBLE);
                    break;
                }
                case "fill_fields" : {
                    Toast.makeText(context, "Enter Phone number", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "google": {
                    signIn();
                    break;
                }
                case "back": {
                    finish();
                    break;
                }
            }
        });



    }

    private void checkPhone() {
        viewModel.checkPhoneNumber().observe(this, checkPhoneModel -> {
            if(checkPhoneModel != null) {
                if (!checkPhoneModel.isStatus()) {
                    Intent intent = new Intent(context, LoginPasswordActivity.class);
                    intent.putExtra("phone", checkPhoneModel.getPhone_number());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, OtpVerificationActivity.class);
                    intent.putExtra("phone", checkPhoneModel.getPhone_number());
                    Log.d("ph",checkPhoneModel.getPhone_number());
                    startActivity(intent);
                }
            } else {
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            }
            binding.progressbar.setVisibility(View.GONE);
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
//                                viewModel.name = user.getDisplayName();
//                                viewModel.email = user.getEmail();
//                                viewModel.startGoogleLogin();
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

}