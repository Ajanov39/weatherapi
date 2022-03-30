package com.ajanovski.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    private static final Retrofit retrofitClient = new Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Retrofit getRetrofitClient() {
        return retrofitClient;
    }

}
