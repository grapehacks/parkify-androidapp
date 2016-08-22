package com.grapeup.parkify.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    }

    protected void initActionBar(){
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(getResources().getDrawable(R.drawable.logo));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.menu_main);

        MenuItem logout = menu.findItem(R.id.menu_logout);
        IconicsDrawable fawSignOut = new IconicsDrawable(this, "faw_sign_out");
        fawSignOut.sizeDp(48);
        fawSignOut.colorRes(R.color.black);
        logout.setIcon(fawSignOut);
        logout.setOnMenuItemClickListener(SIGN_OUT_LISTENER);

        /*MenuItem settings = menu.findItem(R.id.menu_settings);
        IconicsDrawable fawCon = new IconicsDrawable(this, "faw_cog");
        fawCon.sizeDp(48);
        fawCon.colorRes(R.color.black);
        settings.setIcon(fawCon);*/

        MenuItem messages = menu.findItem(R.id.menu_messages);
        IconicsDrawable fawCommenting = new IconicsDrawable(this, "faw_commenting");
        fawCommenting.sizeDp(48);
        fawCommenting.colorRes(R.color.black);
        messages.setIcon(fawCommenting);
        messages.setOnMenuItemClickListener(SHOW_MESSAGES_LISTENER);

        return true;
    }

    private final MenuItem.OnMenuItemClickListener SHOW_MESSAGES_LISTENER = (view) -> {
        String token = UserDataHelper.getToken(SingleFragmentActivity.this);
        Intent intent = new Intent(SingleFragmentActivity.this, MessagesActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        return true;
    };

    private final MenuItem.OnMenuItemClickListener SIGN_OUT_LISTENER = (view) -> {
        UserDataHelper.saveUserInfo(SingleFragmentActivity.this, "", "");
        startActivity(new Intent(SingleFragmentActivity.this, LoginActivity.class));
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
