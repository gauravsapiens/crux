package com.example.crux.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class RetrofitHelper {

    public static SampleService getSampleService() {
        return getRetrofitClient().create(SampleService.class);
    }

    private static Retrofit getRetrofitClient() {
        return new Retrofit.Builder()
                .baseUrl("http://sephora-mobile-takehome-apple.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
