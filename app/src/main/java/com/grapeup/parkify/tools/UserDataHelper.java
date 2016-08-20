package com.grapeup.parkify.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.grapeup.parkify.Constants;

/**
 * @author Pavlo Tymchuk
 */

public class UserDataHelper {
    public static void saveUserInfo(Activity activity, String token, String email) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.USER_TOKEN, token);
        editor.putString(Constants.USER_EMAIL, email);
        editor.commit();
    }

    public static String getToken(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String token = sharedPref.getString(Constants.USER_TOKEN, "");
        return token;
    }

    public static String getEmail(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String email = sharedPref.getString(Constants.USER_EMAIL, "");
        return email;
    }
}
