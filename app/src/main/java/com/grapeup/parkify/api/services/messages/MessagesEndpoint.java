package com.grapeup.parkify.api.services.messages;


import com.grapeup.parkify.api.dto.entity.Message;
import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.api.services.ParkifyEndpoint;
import com.grapeup.parkify.api.services.register.RegisterService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Retrofit;
import rx.Observable;

public class MessagesEndpoint extends ParkifyEndpoint<List<Message>> {

    protected Interceptor getInterceptor() {
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();

            Request request = builder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .addHeader("x-access-token", getToken())
                    .build();
            HttpUrl.Builder httpBuilder = request.url().newBuilder();
            httpBuilder.addEncodedQueryParameter("count", String.valueOf(100));

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    @Override
    protected Observable<List<Message>> requestFromBaseUrl(Retrofit restAdapter) {
        return restAdapter.create(MessageService.class).messages();
    }

    public Observable<Message> readMessage(String messageId) {
        return readMessage(messageId, getRetrofit()).retry(5).debounce(100, TimeUnit.MICROSECONDS);
    }

    private Observable<Message> readMessage(String messageId, Retrofit restAdapter) {
        return restAdapter.create(MessageService.class).readMessage(messageId);
    }
}
