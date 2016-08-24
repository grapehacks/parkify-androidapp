package com.grapeup.parkify.mvp.login;

import com.grapeup.parkify.api.dto.UserDto;
import com.grapeup.parkify.api.services.login.LoginModel;
import com.grapeup.parkify.api.services.login.LoginModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;
import com.grapeup.parkify.tools.UserDataHelper;

import rx.Observer;

public class LoginPresenterImpl extends BasePresenter<LoginView> implements LoginPresenter{

    private LoginModel loginModel;

    public LoginPresenterImpl() {
        loginModel = new LoginModelImpl();
    }

    @Override
    public void login(String email, String password) {
        loginModel.login(email, password).subscribe(new Observer<UserDto>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    getView().onLoginSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().onLoginFailed(e.getMessage());
                }
            }

            @Override
            public void onNext(UserDto user) {
                if (isViewAttached()) {
                    getView().saveUserData(user.getToken(), user.getUser().getEmail());
                    UserDataHelper.setUnreadCount(application, user.getUser().getUnreadMessageCounter());
                }
            }
        });
    }

    @Override
    public void start() {

    }
}
