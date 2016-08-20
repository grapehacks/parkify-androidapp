package com.grapeup.parkify.api.services.login;

import com.grapeup.parkify.api.dto.UserDto;

import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public interface UserService {
    @POST("/authenticate")
    Observable<UserDto> authenticate();
}
