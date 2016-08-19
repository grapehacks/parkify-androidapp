package com.grapeup.parkify.mvp.login;

import android.widget.Toast;

import com.grapeup.parkify.mvp.BasePresenter;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter{

    private LoginModel mLoginModel;

    @Override
    public void login(String username, String password) {
        getView().onLoginFailed();
    }

    @Override
    public void start() {

    }
}
