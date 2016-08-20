package com.grapeup.parkify.api.services.ping;

import com.grapeup.parkify.api.dto.PingDto;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Pavlo Tymchuk
 */
public class PingModelImpl implements PingModel {
    private final PingEndpoint pingEndpoint;

    public PingModelImpl() {
        pingEndpoint = new PingEndpoint();
    }

    @Override
    public Observable<PingDto> ping(String token) {
        pingEndpoint.setToken(token);
        return pingEndpoint.observable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
