package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.mvp.Presenter;

/**
 * @author Pavlo Tymchuk
 */

public interface MainPresenter extends Presenter<MainView> {
    void register(boolean rememberLastChoice);
    void unregister(boolean rememberLastChoice);
}
