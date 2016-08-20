package com.grapeup.parkify.mvp.messages;


import com.grapeup.parkify.mvp.BaseView;
import com.grapeup.parkify.mvp.Presenter;

public interface MessagesContract {

    interface View extends BaseView {
        void onMessagesReceived();

    }

    interface MessagesPresenter extends Presenter<View> {
        void setToken(String token);
    }
}
