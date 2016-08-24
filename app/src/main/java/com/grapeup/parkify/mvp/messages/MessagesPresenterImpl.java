package com.grapeup.parkify.mvp.messages;


import com.google.common.collect.Lists;
import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.api.services.messages.MessageModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;
import com.grapeup.parkify.tools.UserDataHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observer;

public class MessagesPresenterImpl extends BasePresenter<MessagesContract.View> implements MessagesContract.MessagesPresenter{

    public MessagesPresenterImpl() {
        this.messageModel = new MessageModelImpl();
    }

    private com.grapeup.parkify.api.services.messages.MessageModel messageModel;

    @Override
    public void start() {
        if(!isApplicationAttached()) return;

        String token = UserDataHelper.getToken(application);
        messageModel.messages(token).subscribe(new Observer<List<Message>>() {
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
                    getView().onMessagesReceived(getUnreadMessages(messages));
                }
            }
        });
    }

    private List<Message> getUnreadMessages(List<Message> messages) {
        // from end of messages list we want to get last unread messages and send to view
        int unreadCount = UserDataHelper.getUnreadCount(application);
        if(unreadCount < 0){
            unreadCount = messages.size();
        }
        messages = Lists.reverse(messages);

        List<Message> result = new ArrayList<>(unreadCount);
        for(int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (!message.isRead()) {
                result.add(message);
            }
            if (result.size() == unreadCount) {
                break;
            }
        }
        result = Lists.reverse(result);
        return result;
    }

    @Override
    public void readMessage(Message message, MessagesContract.MessageReadResultHandler handler) {
        if(!isApplicationAttached() || message.isRead()) return;

        String token = UserDataHelper.getToken(application);
        messageModel.readMessage(token, message.getId()).subscribe(new Observer<Message>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    handler.failed(e);
                }
            }

            @Override
            public void onNext(Message message) {
                if (isViewAttached()) {
                    setOneLessUnreadCount();
                    handler.success();
                }
            }
        });
    }

    private void setOneLessUnreadCount() {
        int unreadCount = UserDataHelper.getUnreadCount(application) - 1;
        UserDataHelper.setUnreadCount(application, unreadCount);
    }
}
