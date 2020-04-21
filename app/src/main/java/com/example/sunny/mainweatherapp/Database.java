package com.example.sunny.mainweatherapp;

// This was an attempt on making the data persistence on the app
// where it will store the location and weather with the other information
// when the user has no internet connection
// i would obtain the HTTP request sent from the API and save it on the device

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    // The database that will be used in the app
    // variables that will store certain information
    // and table of dataset structure
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_CITY = "London, GB";
    private static final String DATABASE_WEATHER = "12°C";
    private static final String DATABASE_WIND = "1 deg";
    private static final String DATABASE_SUNRISE = "08:00";
    private static final String DATABASE_SUNSET = "18:00";
    private static final String DATABASE_HUMIDITY = "10 hrp";

    public Database(Context context) {
        super(context, DATABASE_CITY, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // the
}
