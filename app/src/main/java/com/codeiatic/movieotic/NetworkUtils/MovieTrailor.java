package com.codeiatic.movieotic.NetworkUtils;

import com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieTrailor {

    @GET("movie/{movie_id}/videos")
    Call<Example> getTrailors(@Path(value = "movie_id") String movie_id, @Query("api_key") String api_key);
}
