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
import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.tools.UserDataHelper;

import java.util.List;

/**
 * Service for retrieving new messages for every minute
 *
 * @author Pavlo Tymchuk
 */
public class MessagesService extends IntentService implements MessagesContract.View {
    public static final String TAG = MessagesService.class.getSimpleName();
    public static final String ACTION = "com.grapeup.parkify.mvp.messages";
    public static final int REQUEST_CODE = 123321;

    private final MessagesContract.MessagesPresenter mMessagesPresenter;
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
        //TODO change to 60
        long interval = 10 * 1000;
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
        mMessagesPresenter = new MessagesPresenterImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMessagesPresenter.attachView(this);
        mMessagesPresenter.attachApplication(getApplication());
        String token = UserDataHelper.getToken(getApplication());
        mMessagesPresenter.setToken(token);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mIntent = intent;
        Log.d(TAG, "Service started.");
        if (UserDataHelper.isUserRegistered(getApplication())) {
            mMessagesPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMessagesReceived(List<Message> messages) {
        //TODO remove
        messages = UserDataHelper.generateMessages();
        if (mMessagesPresenter.receivedNewMessages(messages)) {
            int count = mMessagesPresenter.howMuchReceived(messages);
            if (count > 0) {
                createNotification(count);
            }
        }
    }

    private void createNotification(int count) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setSmallIcon(R.drawable.messages);
        builder.setContentTitle(getResources().getString(R.string.app_name));
        String receivedMessagesString = getResources().getString(R.string.received_messages);
        String formattedString = String.format(receivedMessagesString, "" +count);
        builder.setContentText(formattedString);
        builder.setAutoCancel(true);

        Intent intent = new Intent(getBaseContext(), MessagesActivity.class);
        intent.putExtra(MessagesActivity.ON_FINISH_START_MAIN_ACTIVITY, true);
        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MessagesActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
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
        // star wars theme :D //TODO
        //vibrator.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);
    }

    @Override
    public void onMessagesReceiveError(String message) {
        WakefulBroadcastReceiver.completeWakefulIntent(mIntent);
        mMessagesPresenter.detachView();
    }

    @Override
    public void onMessagesReceiveCompleted() {
        WakefulBroadcastReceiver.completeWakefulIntent(mIntent);
        mMessagesPresenter.detachView();
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
