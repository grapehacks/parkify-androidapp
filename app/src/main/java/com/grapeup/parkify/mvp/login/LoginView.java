package com.grapeup.parkify.mvp.login;

import com.grapeup.parkify.mvp.BaseView;

public interface LoginView extends BaseView {
    void onLoginSuccess();
    void onLoginFailed();
}
