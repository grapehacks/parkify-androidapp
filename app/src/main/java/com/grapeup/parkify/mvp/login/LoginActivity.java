package com.grapeup.parkify.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.services.login.LoginModelImpl;
import com.grapeup.parkify.mvp.main.MainActivity;
import com.grapeup.parkify.tools.UserDataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {
    public static String LOGO_IMAGE = "LoginActivity:logo";
    private LoginPresenter mLoginPresenter;

    @BindView(R.id.editTextUsername) EditText mUsername;
    @BindView(R.id.editTextPassword) EditText mPassword;
    @BindView(R.id.buttonLogin) Button mButtonLogin;
    @BindView(R.id.logo) ImageView mLoginLogo;

    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    public static void launch(AppCompatActivity activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, LOGO_IMAGE);
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(LOGO_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenterImpl(new LoginModelImpl());
        mLoginPresenter.attachView(this);
        mLoginPresenter.attachApplication(getApplication());

        ViewCompat.setTransitionName(mLoginLogo, LOGO_IMAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.buttonLogin)
    public void login() {
        mLoginPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void onLoginSuccess() {
        startActivity(MainActivity.createIntent(this));
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, "Failed to login: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveUserData(String token, String email) {
        UserDataHelper.saveUserInfo(this, token, email);
    }
}
