package com.framgia.marvel.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus on 5/13/2017.
 */

public class ServiceGenerator {
    private static final String BASE_URL = "https://gateway.marvel.com/";

    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okhttpClientBuild = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);

    private static OkHttpClient okHttpClient = okhttpClientBuild.build();

    public static <T> T createService(Class<T> serviceClass) {
        if (retrofit == null) {
            retrofit = builder.client(okHttpClient).build();
        }
        return retrofit.create(serviceClass);
    }
}
