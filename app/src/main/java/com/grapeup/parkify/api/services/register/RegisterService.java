package com.grapeup.parkify.api.services.register;

import com.grapeup.parkify.api.dto.entity.User;

import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public interface RegisterService {
    @POST("/api/participate/register")
    Observable<User> register();

    @POST("/api/participate/unregister")
    Observable<User> unRegister();
}
