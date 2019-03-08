package com.codeiatic.movieotic.NetworkUtils;

import com.codeiatic.movieotic.Models.TvDetailModel.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvDetails {
    @GET("tv/{tv_id}")
    Call<Example> getTvDetails(@Path(value = "tv_id") String tv_id, @Query("api_key") String api_key);
}
