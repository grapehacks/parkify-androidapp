package com.grapeup.parkify.api.services;

import com.grapeup.parkify.api.ParkifyEndpointAPI;
import com.grapeup.parkify.api.dto.BaseDto;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Base Parkify API endpoint
 *
 * @author Pavlo Tymchuk
 */
public abstract class ParkifyEndpoint<T extends BaseDto> extends BaseEndpoint<T> {
    private String email;
    private String password;
    private String token;

    public ParkifyEndpoint() {
        super(new ParkifyEndpointAPI());
    }

    public void setUserData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    protected Interceptor getInterceptor() {
        // Define the interceptor
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            StringBuilder stringBuilder = new StringBuilder();
            if (isAuthRequired()) {
                stringBuilder.append("{\"email\":");
                stringBuilder.append("\"");
                stringBuilder.append(email);
                stringBuilder.append("\",\"password\":");
                stringBuilder.append("\"");
                stringBuilder.append(password);
                stringBuilder.append("\"}");
            }

            RequestBody body = RequestBody.create(JSON, stringBuilder.toString());

            Request request = builder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .post(body).build();
            HttpUrl.Builder httpBuilder = request.url().newBuilder();

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    protected boolean isAuthRequired(){
        return true;
    }
}
