package com.codeiatic.movieotic.NetworkUtils;


import com.codeiatic.movieotic.Models.MovieDetailModel.MovieModel.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDetail {
    @GET("movie/{movie_id}")
    Call<Example> getMovies(@Path(value = "movie_id") String movie_id, @Query("api_key") String api_key);

}
