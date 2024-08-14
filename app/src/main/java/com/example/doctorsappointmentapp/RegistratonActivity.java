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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegistratonActivity extends AppCompatActivity {
    EditText enternum;
    Button getotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraton); // Ensure this matches your XML layout name

        enternum=findViewById(R.id.input_mobile_num);
        getotp=findViewById(R.id.otp_verify);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = enternum.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    enternum.setError("Enter a valid mobile");
                    enternum.requestFocus();
                    return;
                }

                Intent intent = new Intent(RegistratonActivity.this, otp_verification.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
            }
        });
    }

}



