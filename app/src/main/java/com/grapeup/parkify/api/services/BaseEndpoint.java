package com.grapeup.parkify.api.services;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.grapeup.parkify.api.EndpointAPI;
import com.grapeup.parkify.api.dto.BaseDto;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Base class for endpoints
 *
 * @author Pavlo Tymchuk
 */
public abstract class BaseEndpoint<T extends BaseDto> {
    private EndpointAPI mEndpointAPI;

    public BaseEndpoint(EndpointAPI api) {
        mEndpointAPI = api;
    }

    protected Retrofit getRetrofit() {
        // Add interceptor to OkHttpClient
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        // Logging interceptor for requests
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClientBuilder.interceptors().add(getInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        // date deserializer
        final GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Date> dateJsonDeserializer =
                (json, typeOfT, context) -> new Date(Long.valueOf(json.getAsString()) * 1000);
        gsonBuilder.registerTypeAdapter(Date.class, dateJsonDeserializer);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(mEndpointAPI.getApiEndpointUrl())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client);

        return retrofitBuilder.build();
    }

    public Observable<T> observable(){
        return requestFromBaseUrl(getRetrofit()).retry(5).debounce(100, TimeUnit.MICROSECONDS);
    }

    protected abstract Interceptor getInterceptor();

    protected abstract Observable<T> requestFromBaseUrl(Retrofit restAdapter);
}
