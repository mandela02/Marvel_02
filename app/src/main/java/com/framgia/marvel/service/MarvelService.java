package com.framgia.marvel.service;

import com.framgia.marvel.data.model.MarvelModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by asus on 5/13/2017.
 */
public interface MarvelService {
    @GET("v1/public/characters")
    Call<MarvelModel> getMarvel(@Query("ts") String ts, @Query("apikey") String apikey,
                                @Query("hash") String hash, @Query("offset") String offset,
                                @Query("limit") String limit, @Query("name") String name);
}
