package com.grapeup.parkify.mvp;

import android.app.Application;

/**
 * Presenter interface
 *
 * @author Pavlo Tymchuk
 */
public interface Presenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
    void attachApplication(Application application);
    void detachApplication();
    void start();

}
