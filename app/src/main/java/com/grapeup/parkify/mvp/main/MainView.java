package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.mvp.BaseView;

/**
 * @author Pavlo Tymchuk
 */
public interface MainView extends BaseView {
    void userRegistered();
    void userUnregistered();
    void onRegisterFailed(String message);
    void onUnRegisterFailed(String message);
}
