package com.grapeup.parkify.mvp.messages;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.mvp.login.PingPresenter;
import com.grapeup.parkify.mvp.login.PingPresenterImpl;
import com.grapeup.parkify.mvp.login.PingView;
import com.grapeup.parkify.tools.UserDataHelper;

import java.util.Date;

/**
 * Service for retrieving new messages for every minute
 *
 * @author Pavlo Tymchuk
 */
public class MessagesService extends IntentService implements PingView {
    public static final String TAG = MessagesService.class.getSimpleName();
    public static final String ACTION = "com.grapeup.parkify.mvp.messages";
    public static final int REQUEST_CODE = 123321;

    private final PingPresenter mPingPresenter;
    private Intent mIntent;

    public static PendingIntent createPendingIntent(Context context) {
        // Construct an intent that will execute the MessagesDataReceiver
        Intent intent = new Intent(ACTION, null, context, MessagesBroadcastReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        return PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Recurring alarm every minute for fetching messages
    public static void scheduleAlarmForReceivingMessages(Context context) {
        final PendingIntent pIntent = MessagesService.createPendingIntent(context);
        setAlarm(context, pIntent);
    }

    public static void setAlarm(Context context, PendingIntent pIntent) {
        // Setup periodic alarm every minute
        long interval = 60 * 1000;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(), interval, pIntent);
    }

    public static void cancelAlarmForReceivingMessages(Context context) {
        final PendingIntent pIntent = MessagesService.createPendingIntent(context);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    public MessagesService() {
        super(TAG);
        mPingPresenter = new PingPresenterImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPingPresenter.attachView(this);
        mPingPresenter.attachApplication(getApplication());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mIntent = intent;
        Log.d(TAG, "Service started.");
        if (UserDataHelper.isUserRegistered(getApplication())) {
            mPingPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotification(int count) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setSmallIcon(R.drawable.messages);
        builder.setContentTitle(getResources().getString(R.string.app_name));
        String receivedMessagesString = getResources().getString(R.string.received_messages);
        String formattedString = String.format(receivedMessagesString, "" +count);
        builder.setContentText(formattedString);
        builder.setAutoCancel(true);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MessagesActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(MessagesActivity.createIntent(getBaseContext(), true));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, builder.build());
        Vibrator vibrator = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        // star wars theme :D
        vibrator.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);
    }

    @Override
    public void onPingFailed(String message) {
        WakefulBroadcastReceiver.completeWakefulIntent(mIntent);
        mPingPresenter.detachView();
    }

    @Override
    public void setNextDrawDate(Date date) {
        //do nothing
    }

    @Override
    public void tokenIsValid(User user) {
        int savedUnreadCount = UserDataHelper.getUnreadCount(getApplication());
        int unreadMessageCounter = user.getUnreadMsgCounter();

        if (unreadMessageCounter > 0 && savedUnreadCount != unreadMessageCounter) {
            createNotification(unreadMessageCounter);
        }
    }

    @Override
    public void tokenIsInvalid() {

    }

    @Override
    public void onPingCompleted() {
        WakefulBroadcastReceiver.completeWakefulIntent(mIntent);
        mPingPresenter.detachView();
    }

    public static class MessagesBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent i = new Intent(context, MessagesService.class);
            context.startService(i);
        }
    }

    public static class MessagesDeviceBootReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Intent i = new Intent(context, MessagesBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                setAlarm(context, pendingIntent);
            }
        }
    }
}
