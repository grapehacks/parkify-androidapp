package com.grapeup.parkify.mvp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grapeup.parkify.R;
import com.grapeup.parkify.mvp.login.LoginActivity;
import com.grapeup.parkify.tools.UserDataHelper;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsCompatButton;

import butterknife.ButterKnife;

/**
 * Base activity
 *
 * @author Pavlo Tymchuk
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static String TAG = "FRAGMENT_TAG";
    public FragmentManager mFragmentManager;

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            FragmentTransaction transaction = replaceFragment(createFragment(), false);
            transaction.commit();
        }

        initActionBar();
    }

    protected void initActionBar(){
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.menu_main);

        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.getPaint().setColor(Color.GREEN);
        circle.setIntrinsicHeight(120);
        circle.setIntrinsicWidth(120);
        circle.setBounds(0, 0, 120, 120);

        // Get the root inflator.
        LayoutInflater baseInflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View logoutView = baseInflater.inflate(R.layout.item_logout, null);
        IconicsCompatButton logoutBtn = (IconicsCompatButton) logoutView.findViewById(R.id.iconic_btn_logout);
        logoutBtn.setBackground(new IconicsDrawable(this, "faw_sign_out"));
        logoutBtn.setOnClickListener(SIGN_OUT_LISTENER);
        logoutView.setPadding(0,12,24,12);
        MenuItem logout = menu.findItem(R.id.menu_logout);
        logout.setActionView(logoutView);

        View settingsView = baseInflater.inflate(R.layout.item_settings, null);
        IconicsCompatButton settingsBtn = (IconicsCompatButton) settingsView.findViewById(R.id.iconic_btn_settings);
        settingsBtn.setBackground(new IconicsDrawable(this, "faw_cog"));
        settingsView.setPadding(0,12,24,12);
        MenuItem settings = menu.findItem(R.id.menu_settings);
        settings.setActionView(settingsView);

        View messagesView = baseInflater.inflate(R.layout.item_messages, null);
        IconicsCompatButton messagesBtn = (IconicsCompatButton) messagesView.findViewById(R.id.iconic_btn_messages);
        messagesBtn.setBackground(new IconicsDrawable(this, "faw_commenting"));
        messagesView.setPadding(0,12,24,12);
        MenuItem messages = menu.findItem(R.id.menu_messages);
        messages.setActionView(messagesView);

        return true;
    }

    private final View.OnClickListener SIGN_OUT_LISTENER = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserDataHelper.saveUserInfo(SingleFragmentActivity.this, "", "");
            startActivity(new Intent(SingleFragmentActivity.this, LoginActivity.class));
        }
    };

    @NonNull
    private FragmentTransaction replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, TAG);
        if (addBackStack) {
            transaction.addToBackStack(null);
        }
        return transaction;
    }

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.single_activity;
    }

    protected abstract Fragment createFragment();
}
