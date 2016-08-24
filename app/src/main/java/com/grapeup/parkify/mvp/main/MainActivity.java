package com.grapeup.parkify.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.grapeup.parkify.mvp.SingleFragmentActivity;

import java.util.Date;

/**
 * Main activity for application
 *
 * @author Pavlo Tymchuk
 */
public class MainActivity extends SingleFragmentActivity {
    public static final String BUNDLE_DATE = "DATE";

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
        Intent intent = getIntent();
        return MainFragment.getInstance((Date) intent.getSerializableExtra(BUNDLE_DATE));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
