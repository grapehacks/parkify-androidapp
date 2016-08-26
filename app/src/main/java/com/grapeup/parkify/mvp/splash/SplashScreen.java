package com.grapeup.parkify.mvp.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.mvp.login.LoginActivity;
import com.grapeup.parkify.mvp.login.PingPresenterImpl;
import com.grapeup.parkify.mvp.login.PingView;
import com.grapeup.parkify.mvp.main.MainActivity;

import java.util.Date;

/**
 * Splash screen of application
 *
 * @author Pavlo Tymchuk
 */
public class SplashScreen extends AppCompatActivity implements PingView {
    public static final int SPLASH_SCREEN_DELAY = 2000;
    private PingPresenterImpl pingPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pingPresenter = new PingPresenterImpl();
        pingPresenter.attachApplication(getApplication());
        pingPresenter.attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pingPresenter.start();
    }

    @Override
    public void onPingFailed(String message) {
        // redirect to LoginActivity
        startLoginActivity();
    }

    @Override
    public void setNextDrawDate(Date date) {
    }

    @Override
    public void tokenIsValid(User user) {
        // redirect to MainActivity
        startMainActivity();
    }

    @Override
    public void tokenIsInvalid() {
        // redirect to LoginActivity
        startLoginActivity();
    }

    @Override
    public void onPingCompleted() {
    }

    private void startLoginActivity() {
        new Handler().postDelayed(() -> {
                View splashLogo = findViewById(R.id.logo);
                LoginActivity.launch(SplashScreen.this, splashLogo, (String) splashLogo.getTag());
            }, SPLASH_SCREEN_DELAY);
    }

    private void startMainActivity() {
        new Handler().postDelayed(() -> {
                startActivity(MainActivity.createIntent(SplashScreen.this));
            }, SPLASH_SCREEN_DELAY);
    }
}
