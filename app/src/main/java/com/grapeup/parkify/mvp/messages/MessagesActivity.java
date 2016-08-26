package com.grapeup.parkify.mvp.messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.grapeup.parkify.R;
import com.grapeup.parkify.mvp.SingleFragmentActivity;
import com.grapeup.parkify.mvp.main.MainActivity;

public class MessagesActivity extends SingleFragmentActivity {
    public static final String ON_FINISH_START_MAIN_ACTIVITY = "ON_FINISH_START_MAIN_ACTIVITY";
    private boolean startMainActivity;

    public static Intent createIntent(Context context, boolean startMainActivity) {
        Intent intent = new Intent(context, MessagesActivity.class);
        intent.putExtra(MessagesActivity.ON_FINISH_START_MAIN_ACTIVITY, startMainActivity);
        return intent;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startMainActivity = getIntent().getBooleanExtra(ON_FINISH_START_MAIN_ACTIVITY, false);
    }

    @Override
    public boolean onSupportNavigateUp(){
        if (startMainActivity) {
            startActivity(MainActivity.createIntent(this));
        } else {
            finish();
        }
        return true;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected Fragment createFragment() {
        return MessagesFragment.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // do not display menu item - messages because we are in it
        MenuItem menuMessages = menu.findItem(R.id.menu_messages);
        menuMessages.setVisible(false);
        this.invalidateOptionsMenu();
        return true;
    }
}
