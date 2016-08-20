package com.grapeup.parkify.api.services.register;

import com.grapeup.parkify.api.dto.entity.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Pavlo Tymchuk
 */
public class RegisterModelImpl implements RegisterModel {
    private RegisterEndpoint registerEndpoint;

    public RegisterModelImpl() {
        registerEndpoint = new RegisterEndpoint();
    }

    @Override
    public Observable<User> register(String token, boolean rememberLastChoice) {
        registerEndpoint.setToken(token);
        registerEndpoint.setRememberLastChoice(rememberLastChoice);
        return registerEndpoint.register()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<User> unRegister(String token, boolean rememberLastChoice) {
        registerEndpoint.setToken(token);
        registerEndpoint.setRememberLastChoice(rememberLastChoice);
        return registerEndpoint.unRegister()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
