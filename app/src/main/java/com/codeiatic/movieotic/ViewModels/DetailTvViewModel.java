package com.codeiatic.movieotic.ViewModels;

import android.app.Application;
import android.util.Log;

import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example;
import com.codeiatic.movieotic.Repository.TvDetailRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DetailTvViewModel extends AndroidViewModel {
    public static final String TAG = "DETAILTVVIEWMODEL";
    TvDetailRepo tvDetailRepo;

    public LiveData<com.codeiatic.movieotic.Models.TvDetailModel.Example> tvDetails;
    LiveData<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> tvImages;
    LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> tvCastCrew;

    public DetailTvViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "DetailTvViewModel: on constructor");
        tvDetailRepo = new TvDetailRepo();
    }

    public LiveData<Example> getTvCastCrew(String tvId) {
        tvCastCrew = tvDetailRepo.getCrewCast(tvId);
        return tvCastCrew;
    }

    public LiveData<com.codeiatic.movieotic.Models.TvDetailModel.Example> getTvDetails(String tvId) {
        tvDetails = tvDetailRepo.getTvDetails(tvId);
        Log.i(TAG, "getTvDetails: we are in view model" + tvId);
        return tvDetails;
    }

    public LiveData<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example> getTvImages(String tvId) {
        tvImages = tvDetailRepo.getImages(tvId);
        return tvImages;
    }
}
