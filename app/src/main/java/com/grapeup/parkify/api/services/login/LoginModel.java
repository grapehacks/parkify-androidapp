package com.grapeup.parkify.api.services.login;


import com.grapeup.parkify.api.dto.UserDto;

import rx.Observable;

public interface LoginModel {
    Observable<UserDto> login(String email, String password);
}
