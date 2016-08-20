package com.grapeup.parkify.mvp.login;

import com.grapeup.parkify.api.dto.PingDto;
import com.grapeup.parkify.api.services.ping.PingModel;
import com.grapeup.parkify.api.services.ping.PingModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;

import rx.Observer;

/**
 * @author Pavlo Tymchuk
 */

public class PingPresenterImpl extends BasePresenter<PingView> implements PingPresenter {
    private String token;
    private PingModel mPingModel;

    public PingPresenterImpl() {
        mPingModel = new PingModelImpl();
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void start() {
        mPingModel.ping(token).subscribe(new Observer<PingDto>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                getView().onPingFailed(e.getMessage());
            }

            @Override
            public void onNext(PingDto pingDto) {
                getView().setNextDrawDate(pingDto.getDate());
                if (pingDto.getUser() != null) {
                    getView().tokenIsValid();
                } else {
                    getView().tokenIsInvalid();
                }
            }
        });
    }
}
