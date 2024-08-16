package com.example.doctorsappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log; // Import missing for Log
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId;
    private EditText otpInput;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks; // Declare mCallbacks here
    private static final String TAG = "otp_verification"; // Add a TAG for logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification); // Ensure this matches your XML layout name

        mAuth = FirebaseAuth.getInstance();
        otpInput = findViewById(R.id.inputotp);

        // Initialize mCallbacks inside onCreate after the activity is fully initialized
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }

                Toast.makeText(otp_verification.this, "Verification Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                otp_verification.this.verificationId = verificationId;
            }
        };

        // Get the mobile number from the intent
        String mobile = getIntent().getStringExtra("mobno");
        if (mobile != null) {
            sendVerificationCode(mobile);
        }

        findViewById(R.id.otpbtn).setOnClickListener(v -> {
            String code = otpInput.getText().toString().trim();
            if (TextUtils.isEmpty(code) || code.length() < 6) {
                otpInput.setError("Enter valid code");
                otpInput.requestFocus();
                return;
            }
            verifyCode(code);
        });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)             // Use 'mobile' instead of 'phoneNumber'
                        .setTimeout(60L, TimeUnit.SECONDS)  // Timeout and unit
                        .setActivity(this)                 // Activity for callback binding
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Intent intent = new Intent(otp_verification.this, dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign in failed, display a message and update the UI
                        Toast.makeText(otp_verification.this, "Verification Failed", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
