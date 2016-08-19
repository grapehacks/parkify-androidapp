package com.grapeup.parkify.mvp.login;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grapeup.parkify.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    LoginPresenter mLoginPresenter;

    @BindView(R.id.editTextUsername)
    EditText mUsername;

    @BindView(R.id.editTextPassword)
    EditText mPassword;

    @BindView(R.id.buttonLogin)
    Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.attachView(this);
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

    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
    }
}
