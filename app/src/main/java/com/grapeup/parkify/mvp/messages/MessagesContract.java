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
        void setToken(String token);
        void setUnreadCount(int unreadCount);
        boolean receivedNewMessages(List<Message> messages);
        int howMuchReceived(List<Message> messages);
    }
}
