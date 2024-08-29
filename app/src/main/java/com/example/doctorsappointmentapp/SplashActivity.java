package com.example.doctorsappointmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Ensure you have this line to set your splash screen layout

        // Initialize the handler
        handler = new Handler();

        try {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Correct class name to follow Java naming conventions
                    Intent intent = new Intent(SplashActivity.this, dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000); // 3 seconds delay
        } catch (NullPointerException e) {
            Toast.makeText(SplashActivity.this, "Some error: " + e, Toast.LENGTH_LONG).show();
        }
    }
}
