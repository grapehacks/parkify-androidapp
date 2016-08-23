package com.grapeup.parkify.mvp.messages;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * @author Pavlo Tymchuk
 */
public class MessagesDataReceiver extends ResultReceiver {
    private final Receiver receiver;

    public MessagesDataReceiver(Handler handler, Receiver receiver) {
        super(handler);
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }
}
