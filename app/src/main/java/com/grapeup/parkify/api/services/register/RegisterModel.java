package com.grapeup.parkify.api.services.register;

import com.grapeup.parkify.api.dto.entity.User;

import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public interface RegisterModel {
    Observable<User> register(String token, boolean rememberLastChoice);
    Observable<User> unRegister(String token, boolean rememberLastChoice);
}
