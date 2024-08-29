package com.example.doctorsappointmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AppointBook extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appoint_book);

        ed1 = findViewById(R.id.pat_name);
        ed2 = findViewById(R.id.mobnum);
        ed3 = findViewById(R.id.address);
        ed4 = findViewById(R.id.disease);
        bookButton = findViewById(R.id.bookappoint);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from EditTexts and store them as Strings
                String name = ed1.getText().toString().trim();
                String phoneNumber = ed2.getText().toString().trim();
                String address = ed3.getText().toString().trim();
                String disease = ed4.getText().toString().trim();

                // Validate inputs before inserting into the database
                if (name.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() || disease.isEmpty()) {
                    Toast.makeText(AppointBook.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert data into the database
                MyDB myDB = new MyDB(AppointBook.this);
                myDB.bookappoint(name, phoneNumber, address, disease);

                // Provide feedback to the user
                Toast.makeText(AppointBook.this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
