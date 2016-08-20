package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.MessageDto;
import com.grapeup.parkify.api.dto.entity.Message;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MessageModelImpl implements MessageModel{
    private final MessagesEndpoint messagesEndpoint;

    public MessageModelImpl() {
        this.messagesEndpoint = new MessagesEndpoint();
    }

    @Override
    public Observable<List<Message>> messages(String token) {
        messagesEndpoint.setToken(token);
        return messagesEndpoint.observable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
