package com.grapeup.parkify.mvp.main;

import android.support.v4.app.Fragment;

import com.grapeup.parkify.mvp.SingleFragmentActivity;

/**
 * Main activity for application
 *
 * @author Pavlo Tymchuk
 */
public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MainFragment.getInstance();
    }
}
