package com.grapeup.parkify.api.services.login;

import com.grapeup.parkify.api.dto.UserDto;
import com.grapeup.parkify.api.services.ParkifyEndpoint;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */

public class LoginEndpoint extends ParkifyEndpoint<UserDto> {

    @Override
    protected Observable<UserDto> requestFromBaseUrl(Retrofit restAdapter) {
        return restAdapter.create(UserService.class).authenticate();
    }
}
