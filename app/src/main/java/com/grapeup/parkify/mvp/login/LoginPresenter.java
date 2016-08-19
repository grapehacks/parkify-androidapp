package com.grapeup.parkify.mvp.login;


import com.grapeup.parkify.mvp.Presenter;

public interface LoginPresenter extends Presenter<LoginView> {

    void login(String username, String password);
}
