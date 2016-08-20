package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.mvp.Presenter;

/**
 * @author Pavlo Tymchuk
 */

public interface MainPresenter extends Presenter<MainView> {
    void register(String token, boolean rememberLastChoice);
    void unregister(String token, boolean rememberLastChoice);
}
