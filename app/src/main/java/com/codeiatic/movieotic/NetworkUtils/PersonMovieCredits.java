package com.codeiatic.movieotic.NetworkUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PersonMovieCredits {
    @GET("person/{person_id}/movie_credits")
    Call<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> getMovieCredits(@Path(value = "person_id") String person_id, @Query("api_key") String api_key);
}
