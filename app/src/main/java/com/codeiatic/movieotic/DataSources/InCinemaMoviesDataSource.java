package com.codeiatic.movieotic.DataSources;

import android.util.Log;

import com.codeiatic.movieotic.Models.Movies.Example;
import com.codeiatic.movieotic.Models.Movies.Result;
import com.codeiatic.movieotic.NetworkUtils.InCinemaMovies;
import com.codeiatic.movieotic.NetworkUtils.NetworkClient;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InCinemaMoviesDataSource extends PageKeyedDataSource<Integer, Result> {
    public static final int firstPage = 1;

    @Override
    public void loadInitial(@NonNull PageKeyedDataSource.LoadInitialParams<Integer> params, @NonNull final PageKeyedDataSource.LoadInitialCallback<Integer, Result> callback) {
        Retrofit retrofit = NetworkClient.getRetrofit();
        InCinemaMovies inCinemaMovies = retrofit.create(InCinemaMovies.class);
        inCinemaMovies.getMovies("381054badc0dabe1a386b89e1d169c2d", String.valueOf(firstPage))
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getResults(), null, firstPage + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Result> callback) {
        Retrofit retrofit = NetworkClient.getRetrofit();
        InCinemaMovies inCinemaMovies = retrofit.create(InCinemaMovies.class);
        inCinemaMovies.getMovies("381054badc0dabe1a386b89e1d169c2d", String.valueOf(params.key))
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.body() != null) {
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().getResults(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Result> callback) {
        Retrofit retrofit = NetworkClient.getRetrofit();
        InCinemaMovies inCinemaMovies = retrofit.create(InCinemaMovies.class);
        inCinemaMovies.getMovies("381054badc0dabe1a386b89e1d169c2d", String.valueOf(params.key))
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.body() != null) {
                            Integer key = (params.key < response.body().getTotalPages()) ? params.key + 1 : null;
                            Log.i("KEY", "onResponse: "+key);
                            callback.onResult(response.body().getResults(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });
    }
}
