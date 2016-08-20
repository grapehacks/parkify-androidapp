package com.grapeup.parkify.api.services.register;

import com.grapeup.parkify.api.dto.entity.User;
import com.grapeup.parkify.api.services.ParkifyEndpoint;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * @author Pavlo Tymchuk
 */
public class RegisterEndpoint extends ParkifyEndpoint<User> {
    private boolean rememberLastChoice;

    public void setRememberLastChoice(boolean rememberLastChoice) {
        this.rememberLastChoice = rememberLastChoice;
    }

    @Override
    protected Interceptor getInterceptor() {
        // Define the interceptor
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\"rememberLastChoice\":");
            stringBuilder.append(rememberLastChoice);
            stringBuilder.append("}");

            RequestBody body = RequestBody.create(JSON, stringBuilder.toString());

            Request request = builder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .addHeader("x-access-token", getToken())
                    .post(body)
                    .build();
            HttpUrl.Builder httpBuilder = request.url().newBuilder();

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    @Override
    protected Observable<User> requestFromBaseUrl(Retrofit restAdapter) {
        return null;
    }

    public Observable<User> register(){
        return register(getRetrofit()).retry(5).debounce(100, TimeUnit.MICROSECONDS);
    }

    public Observable<User> unRegister(){
        return register(getRetrofit()).retry(5).debounce(100, TimeUnit.MICROSECONDS);
    }

    private Observable<User> register(Retrofit restAdapter) {
        return restAdapter.create(RegisterService.class).register();
    }

    private Observable<User> unRegister(Retrofit restAdapter) {
        return restAdapter.create(RegisterService.class).unRegister();
    }
}
