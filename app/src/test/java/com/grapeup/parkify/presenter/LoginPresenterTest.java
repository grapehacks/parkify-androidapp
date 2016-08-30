package com.grapeup.parkify.presenter;

import com.grapeup.parkify.api.dto.UserDto;
import com.grapeup.parkify.api.services.login.LoginModel;
import com.grapeup.parkify.mvp.login.LoginPresenter;
import com.grapeup.parkify.mvp.login.LoginPresenterImpl;
import com.grapeup.parkify.mvp.login.LoginView;
import com.grapeup.parkify.other.BaseTest;
import com.grapeup.parkify.other.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;

import static com.grapeup.parkify.other.TestConstants.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest extends BaseTest {
    @Mock
    private LoginModel mModel;
    @Mock
    private LoginView mLoginView;
    private LoginPresenter mLoginPresenter;

    @Rule
    // Must be added to every test class that targets app code that uses RxJava
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();


    @Before
    public void setUp() throws Exception {
        super.setUp();
        mLoginPresenter = new LoginPresenterImpl(mModel);
        mLoginPresenter.attachView(mLoginView);
    }

    @After
    public void detachView() {
        mLoginPresenter.detachView();
    }

    @Test
    public void checkLogin() {
        UserDto userDto = mUtils.getGson().fromJson(mUtils.readString("json/userDto.json"), UserDto.class);
        when(mModel.login(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Observable.just(userDto));

        mLoginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD);
        verify(mLoginView).saveUserData(FAKE_TOKEN, FAKE_EMAIL);
        verify(mLoginView).onLoginSuccess();
    }

    @Test
    public void checkLoginFailed() {
        when(mModel.login(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Observable.error(new RuntimeException(WRONG_CREDENTIALS_MESSAGE)));

        mLoginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD);
        verify(mLoginView).onLoginFailed(WRONG_CREDENTIALS_MESSAGE);
    }
}
