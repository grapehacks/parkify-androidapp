package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.entity.Message;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageService {

    @GET("/api/messages")
    Observable<List<Message>> messages();

    @POST("/api/messages/{id}/read")
    Observable<Message> readMessage(@Path("id")String messageId);
}
