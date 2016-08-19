package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.mvp.BaseView;
import com.grapeup.parkify.mvp.Presenter;

/**
 * Main contract
 *
 * @author Pavlo Tymchuk
 */
public interface MainContract {

    interface View extends BaseView {

    }

    interface MainPresenter extends Presenter<View> {

    }
}
