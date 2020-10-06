package com.example.localpatientsapp.Utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String PREFERENCE_USER = "preference_user";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static String getCurrentDateTime(String format) {
        DateFormat dateFormatter = new SimpleDateFormat(format);
        dateFormatter.setLenient(false);
        dateFormatter.setTimeZone(Calendar.getInstance().getTimeZone());
        Date today = new Date();
        String s = dateFormatter.format(today);
        Log.e("today_Date", "" + s);
        return s;
    }
}
