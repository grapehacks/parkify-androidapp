package com.grapeup.parkify.api.services.ping;

import com.grapeup.parkify.api.dto.PingDto;
import com.grapeup.parkify.api.dto.UserDto;

import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public interface PingModel {
    Observable<PingDto> ping(String token);
}
