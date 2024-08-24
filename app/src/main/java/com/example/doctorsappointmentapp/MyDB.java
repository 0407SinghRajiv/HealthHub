package com.example.doctorsappointmentapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Appointment DB";
    private static final String TABLE_APPOINTMENT = "Appointment";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PNUM = "PHONE_NUMBER" ;
    private static final String KEY_ADD = "ADDRESS";
    private static final String KEY_DISEASE = "DISEASE";

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE"+ TABLE_APPOINTMENT+
                "(" + KEY_NAME + "STRING," + KEY_PNUM+ "TEXT," + KEY_ADD + "TEXT," + KEY_DISEASE + "TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_APPOINTMENT);

        onCreate(db);
    }
}
