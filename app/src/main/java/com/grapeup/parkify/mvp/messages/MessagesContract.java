package com.grapeup.parkify.mvp.messages;


import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.mvp.BaseView;
import com.grapeup.parkify.mvp.Presenter;

import java.util.List;

public interface MessagesContract {

    interface View extends BaseView {
        void onMessagesReceived(List<Message> messages);
        void onMessagesReceiveError(String message);
        void onMessagesReceiveCompleted();
    }

    interface MessagesPresenter extends Presenter<View> {
        void readMessage(Message message, MessageReadResultHandler handler);
    }

    interface MessageReadResultHandler {
        void success();
        void failed(Throwable e);
    }
}
