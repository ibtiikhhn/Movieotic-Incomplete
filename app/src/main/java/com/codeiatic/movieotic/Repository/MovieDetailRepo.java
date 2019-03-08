package com.codeiatic.movieotic.Repository;

import com.codeiatic.movieotic.Models.MovieDetailModel.MovieModel.Example;
import com.codeiatic.movieotic.NetworkUtils.MovieCrew;
import com.codeiatic.movieotic.NetworkUtils.MovieDetail;
import com.codeiatic.movieotic.NetworkUtils.MovieTrailor;
import com.codeiatic.movieotic.NetworkUtils.NetworkClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailRepo {
    private static final String TAG = "MovieDetailRepo";
    private Retrofit retrofit;

    public MovieDetailRepo() {
        retrofit = NetworkClient.getRetrofit();
    }

    public LiveData<Example> getMovieDetail(String movieId) {
        MovieDetail movieDetail = retrofit.create(MovieDetail.class);
        Call<Example> call = movieDetail.getMovies(movieId, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData data = new MutableLiveData();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }



    public LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> getMovieTrailors(String movieId) {
        MovieTrailor movieTrailor = retrofit.create(MovieTrailor.class);
        Call<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> call = movieTrailor.getTrailors(movieId, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData mutableLiveData = new MutableLiveData();
        call.enqueue(new Callback<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> call, Response<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }

    public LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> getMovieCrewCast(String movieid) {
        MovieCrew movieCrew = retrofit.create(MovieCrew.class);

        Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> call = movieCrew.getCastCrew(movieid, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData mutableLiveData = new MutableLiveData();
        call.enqueue(new Callback<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> call, Response<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }


}
