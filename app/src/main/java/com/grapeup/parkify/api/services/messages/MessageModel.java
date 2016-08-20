package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.MessageDto;
import com.grapeup.parkify.api.dto.entity.Message;

import java.util.List;

import rx.Observable;

public interface MessageModel {
    Observable<List<Message>> messages(String token);
}
