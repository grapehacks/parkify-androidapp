package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.api.services.register.RegisterModel;
import com.grapeup.parkify.api.services.register.RegisterModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;

import rx.Observer;

/**
 * @author Pavlo Tymchuk
 */
public class MainPresenterImpl extends BasePresenter<MainView> implements MainPresenter {
    private RegisterModel registerModel;

    public MainPresenterImpl() {
        registerModel = new RegisterModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(String token, boolean rememberLastChoice) {
        registerModel.register(token, rememberLastChoice).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().onRegisterFailed(e.getMessage());
            }

            @Override
            public void onNext(User user) {
                getView().userRegistered();
            }
        });
    }

    @Override
    public void unregister(String token, boolean rememberLastChoice) {
        registerModel.unRegister(token, rememberLastChoice).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().onUnRegisterFailed(e.getMessage());
            }

            @Override
            public void onNext(User user) {
                getView().userUnregistered();
            }
        });
    }
}
