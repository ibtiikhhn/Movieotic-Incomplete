package com.codeiatic.movieotic.NetworkUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvCrew {
    @GET("tv/{tv_id}/credits")
    Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> getCastCrew(@Path(value = "tv_id") String tv_id, @Query("api_key") String api_key);
}
