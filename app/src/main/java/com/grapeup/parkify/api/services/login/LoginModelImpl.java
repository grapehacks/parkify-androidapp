package com.grapeup.parkify.api.services.login;


import com.grapeup.parkify.api.dto.UserDto;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginModelImpl implements LoginModel{
    private final LoginEndpoint loginEndpoint;

    public LoginModelImpl() {
        loginEndpoint = new LoginEndpoint();
    }

    @Override
    public Observable<UserDto> login(String email, String password) {
        loginEndpoint.setUserData(email, password);
        return loginEndpoint.observable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
