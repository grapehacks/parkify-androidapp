package com.grapeup.parkify.api.services.ping;

import com.grapeup.parkify.api.dto.PingDto;
import com.grapeup.parkify.api.services.ParkifyEndpoint;

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
public class PingEndpoint extends ParkifyEndpoint<PingDto>{

    @Override
    protected Interceptor getInterceptor() {
        // Define the interceptor
        Interceptor interceptor = (chain) -> {
            Request.Builder builder = chain.request().newBuilder();

            Request request = builder
                    .addHeader("Content-Type", "application/json")
                    .addHeader("UserDto-Agent", "Parkify-App")
                    .addHeader("x-access-token", getToken())
                    .build();
            HttpUrl.Builder httpBuilder = request.url().newBuilder();

            request = request.newBuilder().url(httpBuilder.build()).build();
            return chain.proceed(request);
        };

        return interceptor;
    }

    @Override
    protected Observable<PingDto> requestFromBaseUrl(Retrofit restAdapter) {
        return restAdapter.create(PingService.class).ping();
    }
}
