package com.hanjullo.hanjulloapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "https://hanjullo.akkyu.net";
    private static Retrofit INSTANCE = null;

    public RetrofitClient() { }

    public static Retrofit getClient() {

        if (INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return INSTANCE;
    }
}
