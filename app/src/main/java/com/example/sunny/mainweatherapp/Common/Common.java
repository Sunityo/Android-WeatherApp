package com.example.sunny.mainweatherapp.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Common {

    // Adding the API key from openWeatherMap
    public static final String APP_ID = "bcdf16420fc6f5b1c55b538a5e4fc5a7";

    // the current location is set a null, because Dexter will call for the location of the user.
    public static Location current_location=null;

    // This converts the date from the API in a format that the user can understand well
    // a new simpledateforma is made under the name "sim"
    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sim = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted = sim.format(date);
        return formatted;

    }
    // we do the same for the sunset and sunrise ability from the API
    // this converts the time for the location that the user has
    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sim = new SimpleDateFormat("HH:mm");
        String formatted = sim.format(date);
        return formatted;
    }
}
