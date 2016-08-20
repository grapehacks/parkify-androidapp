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

    public static void setUserIsRegistered(Activity activity, boolean isUserRegistered) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.IS_REGISTERED, isUserRegistered);
        editor.commit();
    }

    public static boolean isUserRegistered(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Constants.IS_REGISTERED, false);
    }

    public static void setRememberLastChoice(Activity activity, boolean isRememberLastChoice) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.IS_REMEMBER_LAST_CHOICE, isRememberLastChoice);
        editor.commit();
    }

    public static boolean isRememberLastChoice(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(Constants.IS_REMEMBER_LAST_CHOICE, false);
    }
}
