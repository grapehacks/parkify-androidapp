package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.MessageDto;
import com.grapeup.parkify.api.dto.entity.Message;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface MessageService {

    @GET("/api/messages")
    Observable<List<Message>> messages();


}
