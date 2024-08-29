package com.example.doctorsappointmentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppointmentDB";  // Renamed to avoid spaces
    private static final int DATABASE_VERSION = 1;  // Added database version
    private static final String TABLE_APPOINTMENT = "Appointment";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PNUM = "PHONE_NUMBER";
    private static final String KEY_ADD = "ADDRESS";
    private static final String KEY_DISEASE = "DISEASE";

    public MyDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Corrected SQL with proper spacing, valid data types, and removed extra semicolon
        String CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_APPOINTMENT + "("
                + KEY_NAME + " TEXT, "
                + KEY_PNUM + " TEXT, "  // Use TEXT for phone number
                + KEY_ADD + " TEXT, "
                + KEY_DISEASE + " TEXT" + ")";
        db.execSQL(CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENT);
        onCreate(db);
    }

    // Method to insert a new appointment into the database
    void bookappoint(String name, String number, String address, String disease) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, name);
        cv.put(KEY_PNUM, number);
        cv.put(KEY_ADD, address);
        cv.put(KEY_DISEASE, disease);

        // Insert the data and check the result for success/failure
        long result = db.insert(TABLE_APPOINTMENT, null, cv);
        if (result == -1) {
            // Insertion failed
            System.out.println("Failed to insert appointment.");
        } else {
            // Insertion succeeded
            System.out.println("Appointment booked successfully.");
        }

        // Always close the database connection when done
        db.close();
    }
}
