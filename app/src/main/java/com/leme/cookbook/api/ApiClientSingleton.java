package com.leme.cookbook.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum ApiClientSingleton {
    API_CLIENT_INSTANCE;

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private Retrofit retrofit;

    public Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
