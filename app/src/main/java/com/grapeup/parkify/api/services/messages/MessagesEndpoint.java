package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.MessageDto;
import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.api.services.ParkifyEndpoint;

import java.util.List;

import okhttp3.Request;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import retrofit2.Retrofit;
import rx.Observable;

public class MessagesEndpoint extends ParkifyEndpoint<List<Message>> {

    private int unreadCount;

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    protected Interceptor getInterceptor() {
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();

            Request request = builder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .addHeader("x-access-token", getToken())
                    .build();
            HttpUrl.Builder httpBuilder = request.url().newBuilder();
            if (unreadCount != -1) {
                httpBuilder.addEncodedQueryParameter("count", String.valueOf(unreadCount));
            }

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    @Override
    protected Observable<List<Message>> requestFromBaseUrl(Retrofit restAdapter) {
        return restAdapter.create(MessageService.class).messages();
    }
}
