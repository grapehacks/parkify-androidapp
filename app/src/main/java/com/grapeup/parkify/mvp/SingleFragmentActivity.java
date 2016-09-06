package com.grapeup.parkify.mvp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.grapeup.parkify.R;
import com.grapeup.parkify.mvp.login.LoginActivity;
import com.grapeup.parkify.mvp.messages.MessagesActivity;
import com.grapeup.parkify.tools.UserDataHelper;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Base activity
 *
 * @author Pavlo Tymchuk
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static String TAG = "FRAGMENT_TAG";
    public FragmentManager mFragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public AlertDialog mLogOutDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            FragmentTransaction transaction = replaceFragment(createFragment(), false);
            transaction.commit();
        }

        initActionBar();
        initLogOutDialog();
    }

    private void initLogOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_logout);
        builder.setPositiveButton(R.string.dialog_positive_btn, (dialog, v2) -> {

            UserDataHelper.saveUserInfo(SingleFragmentActivity.this, "", "");
            UserDataHelper.setUnreadCount(SingleFragmentActivity.this, -1);
            UserDataHelper.setRememberLastChoice(SingleFragmentActivity.this, false);
            UserDataHelper.setUserIsRegistered(SingleFragmentActivity.this, false);
            dialog.dismiss();

            finish();
            startActivity(LoginActivity.createIntent(this));
        });
        builder.setNegativeButton(R.string.dialog_negative_btn, (dialog, v1) -> {
        });
        builder.setCancelable(false);
        mLogOutDialog = builder.create();
    }

    protected void initActionBar(){
        setSupportActionBar(toolbar);
        if (showBackButton()) {
            getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(getResources().getDrawable(R.drawable.logo));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    protected abstract boolean showBackButton();

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.menu_main);

        /*MenuItem settings = menu.findItem(R.id.menu_settings);
        IconicsDrawable fawCon = new IconicsDrawable(this, "faw_cog");
        fawCon.sizeDp(48);
        fawCon.colorRes(R.color.black);
        settings.setIcon(fawCon);*/

        MenuItem messages = menu.findItem(R.id.menu_messages);
        IconicsDrawable fawCommenting = new IconicsDrawable(this, "faw_commenting"){
            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);
                createCircleWithMessagesCount(canvas, this);
            }
        };
        fawCommenting.sizeDp(48);
        fawCommenting.colorRes(R.color.black);
        messages.setIcon(fawCommenting);
        messages.setOnMenuItemClickListener(SHOW_MESSAGES_LISTENER);

        MenuItem logout = menu.findItem(R.id.menu_logout);
        IconicsDrawable fawSignOut = new IconicsDrawable(this, "faw_sign_out");
        fawSignOut.sizeDp(48);
        fawSignOut.colorRes(R.color.black);
        logout.setIcon(fawSignOut);
        logout.setOnMenuItemClickListener(SIGN_OUT_LISTENER);
        return true;
    }

    private void createCircleWithMessagesCount(Canvas canvas, IconicsDrawable drawable) {
        Rect bounds = drawable.getBounds();
        int width = bounds.width();
        int height = bounds.height();


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.red_light));
        canvas.drawCircle(width - width / 3, height / 3, width / 4, paint);
    }

    private final MenuItem.OnMenuItemClickListener SHOW_MESSAGES_LISTENER = (view) -> {
        startActivity(MessagesActivity.createIntent(this, false));
        return true;
    };

    private final MenuItem.OnMenuItemClickListener SIGN_OUT_LISTENER = (view) -> {
        mLogOutDialog.show();
        return true;
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
