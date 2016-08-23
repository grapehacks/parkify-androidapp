package com.grapeup.parkify.mvp.messages;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

/**
 * Service for retrieving new messages for every minute
 *
 * @author Pavlo Tymchuk
 */
public class MessagesService extends IntentService{
    public static final String TAG = MessagesService.class.getSimpleName();
    public static final String RECEIVER = "RECEIVER";

    public static Intent createIntent(Context context, MessagesDataReceiver receiver) {
        Intent intent = new Intent(Intent.ACTION_SYNC, null, context, MessagesService.class);
        intent.putExtra(RECEIVER, receiver);
        return intent;
    }

    public MessagesService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Service started.");

        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
    }
}
