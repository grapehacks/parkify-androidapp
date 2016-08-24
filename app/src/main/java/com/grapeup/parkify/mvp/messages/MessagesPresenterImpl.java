package com.grapeup.parkify.mvp.messages;


import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.api.services.messages.MessageModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;
import com.grapeup.parkify.tools.UserDataHelper;

import java.util.List;

import rx.Observer;

public class MessagesPresenterImpl extends BasePresenter<MessagesContract.View> implements MessagesContract.MessagesPresenter{

    private String token;
    private int unreadCount;

    public MessagesPresenterImpl() {
        this.messageModel = new MessageModelImpl();
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public boolean receivedNewMessages(List<Message> messages) {
        if (application == null) return false;

        long lastMessageTime = UserDataHelper.getLastMessageTime(application);
        for (Message message : messages) {
            if (message.getDate().getTime() == lastMessageTime) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int howMuchReceived(List<Message> messages) {
        if (application == null) return 0;

        int result = messages.size();
        int index = -1;
        long lastMessageTime = UserDataHelper.getLastMessageTime(application);
        for (Message message : messages) {
            if (message.getDate().getTime() == lastMessageTime) {
                index = messages.indexOf(message);
            }
        }

        if (index > 0) {
            result = messages.subList(index, messages.size()).size();
        }

        return result;
    }

    private com.grapeup.parkify.api.services.messages.MessageModel messageModel;

    @Override
    public void start() {
        messageModel.messages(token, unreadCount).subscribe(new Observer<List<Message>>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    getView().onMessagesReceiveCompleted();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().onMessagesReceiveError(e.getMessage());
                }
            }

            @Override
            public void onNext(List<Message> messages) {
                if (isViewAttached()) {
                    //TODO create logic for displaying messages
                    UserDataHelper.setReceivedCount(application, messages.size());
                    getView().onMessagesReceived(messages);
                }
            }
        });
    }
}
