package com.grapeup.parkify.mvp.login;

import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.mvp.BaseView;

import java.util.Date;

/**
 * @author Pavlo Tymchuk
 */
public interface PingView extends BaseView {
    void onPingFailed(String message);
    void setNextDrawDate(Date date);
    void tokenIsValid(User user);
    void tokenIsInvalid();
    void onPingCompleted();
}
