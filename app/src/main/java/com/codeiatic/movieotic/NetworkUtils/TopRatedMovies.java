package com.codeiatic.movieotic.NetworkUtils;


import com.codeiatic.movieotic.Models.Movies.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopRatedMovies {
    @GET("movie/top_rated")
    Call<Example> getMovies(@Query("api_key") String api_key, @Query("page") String page);
}
