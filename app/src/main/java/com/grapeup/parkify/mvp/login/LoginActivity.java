package com.grapeup.parkify.mvp.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grapeup.parkify.R;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.mvp.main.MainActivity;
import com.grapeup.parkify.tools.UserDataHelper;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView, PingView {

    private LoginPresenter mLoginPresenter;
    private PingPresenter mPingPresenter;

    @BindView(R.id.editTextUsername)
    EditText mUsername;

    @BindView(R.id.editTextPassword)
    EditText mPassword;
    @BindView(R.id.buttonLogin)
    Button mButtonLogin;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.attachView(this);
        mLoginPresenter.attachApplication(getApplication());

        mPingPresenter = new PingPresenterImpl();
        mPingPresenter.attachView(this);
        mPingPresenter.attachApplication(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPingPresenter.start();
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
        startMainActivity();
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, "Failed to login: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveUserData(String token, String email) {
        UserDataHelper.saveUserInfo(this, token, email);
    }

    @Override
    public void onPingFailed(String message) {
        Toast.makeText(this, "Failed to login: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void tokenIsValid(User user) {
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(MainActivity.BUNDLE_DATE, date);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void tokenIsInvalid() {
        // do nothing
    }

    @Override
    public void onPingCompleted() {

    }

    @Override
    public void setNextDrawDate(Date date) {
        this.date = date;
    }
}
