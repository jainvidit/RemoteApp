package com.haloappstudio.morld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by suheb on 26/5/16.
 */
public class Utils {
    public static final String API_BASE_URL = "http://morldapp.herokuapp.com/";
    public static final String USER = "user";
    public static final String LOCATION = "location";
    public static final String MY_PREFS = "myprefs";
    public static final String USER_ID = "userid";

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_ID);
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

