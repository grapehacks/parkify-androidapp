package com.grapeup.parkify.mvp.messages;


import com.grapeup.parkify.api.dto.MessageDto;
import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.api.services.messages.*;
import com.grapeup.parkify.api.services.messages.MessageModel;
import com.grapeup.parkify.api.services.messages.MessageModelImpl;
import com.grapeup.parkify.mvp.BasePresenter;

import java.util.List;

import rx.Observer;

public class MessagesPresenterImpl extends BasePresenter<MessagesContract.View> implements MessagesContract.MessagesPresenter{

    private String token;

    public MessagesPresenterImpl() {
        this.messageModel = new MessageModelImpl();
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    private com.grapeup.parkify.api.services.messages.MessageModel messageModel;

    @Override
    public void start() {
        messageModel.messages(token).subscribe(new Observer<List<Message>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Message> messageDto) {

            }
        });
    }
}
