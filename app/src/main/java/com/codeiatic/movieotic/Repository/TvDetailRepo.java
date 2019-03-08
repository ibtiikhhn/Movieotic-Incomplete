package com.codeiatic.movieotic.Repository;

import android.util.Log;

import com.codeiatic.movieotic.Models.TvDetailModel.Example;
import com.codeiatic.movieotic.NetworkUtils.NetworkClient;
import com.codeiatic.movieotic.NetworkUtils.TvCrew;
import com.codeiatic.movieotic.NetworkUtils.TvDetails;
import com.codeiatic.movieotic.NetworkUtils.TvImages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvDetailRepo {
    public static final String TAG = "TvDetailRepo";
    Retrofit retrofit;

    public TvDetailRepo() {
        retrofit = NetworkClient.getRetrofit();
    }

    public LiveData<Example> getTvDetails(final String tvId) {
        TvDetails tvDetails = retrofit.create(TvDetails.class);
        final MutableLiveData mutableLiveData = new MutableLiveData();

        Call<Example> call = tvDetails.getTvDetails(tvId, "381054badc0dabe1a386b89e1d169c2d");

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Log.i(TAG, "onResponse: "+response.message());
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                    Log.i(TAG, "onResponse: we are in the repo" + response.message() + " " + tvId);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                mutableLiveData.setValue(null);
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });

        return mutableLiveData;
    }


    public LiveData<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> getImages(String tvId) {
        TvImages images = retrofit.create(TvImages.class);
        final MutableLiveData mutableLiveData = new MutableLiveData();
        images.getPosters(tvId,"381054badc0dabe1a386b89e1d169c2d").enqueue(new Callback<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> call, Response<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> getCrewCast(String tvId) {
        TvCrew tvCrew = retrofit.create(TvCrew.class);
        final MutableLiveData mutableLiveData = new MutableLiveData();
        tvCrew.getCastCrew(tvId,"381054badc0dabe1a386b89e1d169c2d").enqueue(new Callback<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> call, Response<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

}
