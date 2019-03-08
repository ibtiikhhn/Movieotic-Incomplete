package com.codeiatic.movieotic.NetworkUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvImages {
    @GET("tv/{tv_id}/images")
    Call<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> getPosters(@Path(value = "tv_id") String tv_id, @Query("api_key") String api_key);
}
