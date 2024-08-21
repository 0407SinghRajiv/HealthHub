package com.example.doctorsappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button otpbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification); // Ensure this matches your XML layout name

        mAuth = FirebaseAuth.getInstance();
        otpInput = findViewById(R.id.inputotp);
        otpbtn = findViewById(R.id.otpbtn);  // Initialize the button here

        String mobile = getIntent().getStringExtra("mobile");
        sendVerificationCode(mobile);

        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otpInput.getText().toString();
                if (!code.isEmpty()) {
                    verifyCode(code);
                } else {
                    Toast.makeText(otp_verification.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                        verifyCode(code);
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

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign-in succeeded, navigate to the Dashboard
                       // Intent intent = new Intent(otp_verification.this, dashboard.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(intent);
                    } else {
                        // Sign-in failed, display a message and update the UI
                        Toast.makeText(otp_verification.this, "Verification failed, please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}