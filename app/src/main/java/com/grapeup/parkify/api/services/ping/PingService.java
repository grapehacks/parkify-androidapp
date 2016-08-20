package com.grapeup.parkify.api.services.ping;

import com.grapeup.parkify.api.dto.PingDto;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public interface PingService {
    @GET("/ping")
    Observable<PingDto> ping();
}
