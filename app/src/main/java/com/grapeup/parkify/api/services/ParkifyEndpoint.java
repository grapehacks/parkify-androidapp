package com.grapeup.parkify.api.services;

import com.grapeup.parkify.api.ParkifyEndpointAPI;
import com.grapeup.parkify.api.dto.BaseDto;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Base Parkify API endpoint
 *
 * @author Pavlo Tymchuk
 */
public abstract class ParkifyEndpoint<T extends BaseDto> extends BaseEndpoint<T> {

    public ParkifyEndpoint() {
        super(new ParkifyEndpointAPI());
    }

    @Override
    protected Interceptor getInterceptor() {
        // Define the interceptor
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();
            Request request = builder
                    .addHeader("Accept", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .build();

            HttpUrl.Builder httpBuilder = request.url().newBuilder();
            if (isAuthRequired()) {
                // TODO if token is required for authentication
                //httpBuilder.addEncodedQueryParameter(ACCESS_TOKEN, token);
            }

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    protected boolean isAuthRequired(){
        return false;
    }
}
