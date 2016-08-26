package com.grapeup.parkify.mvp.main;

import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.api.services.register.RegisterModel;
import com.grapeup.parkify.api.services.register.RegisterModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;
import com.grapeup.parkify.tools.UserDataHelper;

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
    public void register(boolean rememberLastChoice) {
        if(!isApplicationAttached()) return;

        String token = UserDataHelper.getToken(application);
        registerModel.register(token, rememberLastChoice).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().onRegisterFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(User user) {
                if (isViewAttached()) {
                    UserDataHelper.setRememberLastChoice(application, rememberLastChoice);
                    UserDataHelper.setUserIsRegistered(application, true);
                    getView().userRegistered();
                }
            }
        });
    }

    @Override
    public void unregister(boolean rememberLastChoice) {
        if(!isApplicationAttached()) return;

        String token = UserDataHelper.getToken(application);
        registerModel.unRegister(token, rememberLastChoice).subscribe(new Observer<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().onUnRegisterFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(User user) {
                if (isViewAttached()) {
                    UserDataHelper.setRememberLastChoice(application, rememberLastChoice);
                    UserDataHelper.setUserIsRegistered(application, false);
                    getView().userUnregistered();
                }
            }
        });
    }
}
