package com.grapeup.parkify.tools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.grapeup.parkify.Constants;
import com.grapeup.parkify.api.dto.entity.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    public static String getToken(Application application) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
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

    public static boolean isUserRegistered(ContextWrapper contextWrapper) {
        SharedPreferences sharedPref = contextWrapper.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
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

    public static void setLastMessageTime(Application application, long time) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(Constants.LAST_MESSAGE_TIME, time);
        editor.commit();
    }

    public static long getLastMessageTime(Application application) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getLong(Constants.LAST_MESSAGE_TIME, -1L);
    }

    public static void setUnreadCount(ContextWrapper application, int unreadCount) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constants.UNREAD_COUNT, unreadCount);
    }

    public static int getUnreadCount(ContextWrapper application) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(Constants.UNREAD_COUNT, -1);
    }

    public static void setReceivedCount(ContextWrapper application, int receivedCount) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(Constants.RECEIVED_COUNT, receivedCount);
    }

    public static int getReceivedCount(ContextWrapper application) {
        SharedPreferences sharedPref = application.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getInt(Constants.RECEIVED_COUNT, 0);
    }

    //TODO remove
    public static List<Message> generateMessages() {
        List<Message> result = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            Message message = new Message();
            message.setDate(new Date(2016, 1, 1, 10, 0));
            message.setRead(false);
            message.setText("Text" + i);
            message.setTopic("Topic" + i);
            message.setType(random.nextInt(4));
            result.add(message);
        }

        return result;
    }
}
