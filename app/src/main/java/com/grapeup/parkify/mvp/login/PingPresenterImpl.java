package com.grapeup.parkify.mvp.login;

import com.grapeup.parkify.api.dto.PingDto;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.api.services.ping.PingModel;
import com.grapeup.parkify.api.services.ping.PingModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;
import com.grapeup.parkify.tools.UserDataHelper;

import rx.Observer;

/**
 * @author Pavlo Tymchuk
 */

public class PingPresenterImpl extends BasePresenter<PingView> implements PingPresenter {
    private PingModel mPingModel;

    public PingPresenterImpl() {
        mPingModel = new PingModelImpl();
    }

    @Override
    public void start() {
        if(!isApplicationAttached()) return;

        String token = UserDataHelper.getToken(application);
        mPingModel.ping(token).subscribe(new Observer<PingDto>() {
            @Override
            public void onCompleted() {
                if (!isViewAttached()) return;

                getView().onPingCompleted();
            }

            @Override
            public void onError(Throwable e) {
                getView().onPingFailed(e.getMessage());
            }

            @Override
            public void onNext(PingDto pingDto) {
                getView().setNextDrawDate(pingDto.getDate());
                User user = pingDto.getUser();
                if (user != null) {
                    getView().tokenIsValid(user);
                } else {
                    getView().tokenIsInvalid();
                }
            }
        });
    }
}
