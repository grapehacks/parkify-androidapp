package com.grapeup.parkify.mvp;

/**
 * Presenter interface
 *
 * @author Pavlo Tymchuk
 */
public interface Presenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

    void start();

}
