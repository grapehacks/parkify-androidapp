package com.grapeup.parkify.mvp.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.grapeup.parkify.mvp.SingleFragmentActivity;

public class MessagesActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected Fragment createFragment() {
        return MessagesFragment.getInstance();
    }
}
