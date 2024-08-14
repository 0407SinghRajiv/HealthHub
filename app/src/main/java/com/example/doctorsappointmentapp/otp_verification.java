package com.example.doctorsappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId;
    private EditText otpInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification); // Ensure this matches your XML layout name

        mAuth = FirebaseAuth.getInstance();
        otpInput = findViewById(R.id.inputotp);

        String mobile = getIntent().getStringExtra("mobile");
        sendVerificationCode(mobile);
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+91" + mobile)        // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS)     // Timeout and unit
                .setActivity(this)                    // Activity (for callback binding)
                .setCallbacks(mCallbacks)             // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    // Auto-detection of the code
                    String code = credential.getSmsCode();
                    if (code != null) {
                        otpInput.setText(code);  // Automatically inputs the OTP
                        // You can verify the code here
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    // Handle the error
                    Toast.makeText(otp_verification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(s, token);
                    verificationId = s;
                    // The verification code has been sent, store the verificationId for later use
                }
            };
}


