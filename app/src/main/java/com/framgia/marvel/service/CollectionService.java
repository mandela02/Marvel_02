package com.framgia.marvel.service;

import com.framgia.marvel.data.model.MarvelModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bruce Wayne on 5/23/2017.
 */
public interface CollectionService {
    @GET("v1/public/characters/{collectionId}/{comics}")
    Call<MarvelModel> getMarvel(
        @Path("collectionId") String collectionId, @Path("comics") String comics,
        @Query("ts") String ts, @Query("apikey") String apikey,
        @Query("hash") String hash, @Query("limit") String limit);
}
