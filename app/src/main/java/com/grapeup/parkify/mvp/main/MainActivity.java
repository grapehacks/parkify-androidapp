package com.grapeup.parkify.mvp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.grapeup.parkify.mvp.SingleFragmentActivity;

/**
 * Main activity for application
 *
 * @author Pavlo Tymchuk
 */
public class MainActivity extends SingleFragmentActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected Fragment createFragment() {
        return MainFragment.getInstance();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
