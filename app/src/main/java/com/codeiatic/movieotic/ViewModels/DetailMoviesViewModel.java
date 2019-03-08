package com.codeiatic.movieotic.ViewModels;

import android.app.Application;

import com.codeiatic.movieotic.Models.MovieDetailModel.MovieModel.Example;
import com.codeiatic.movieotic.Repository.MovieDetailRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DetailMoviesViewModel extends AndroidViewModel {

    public static final String TAG = "DETAILMOVIESVIEWMODEL";

    MovieDetailRepo movieDetailRepo;


    LiveData<Example> movieDetail;
    LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> movieTrailor;
    LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> moveCrewCast;


    public DetailMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDetailRepo = new MovieDetailRepo();
    }

    public LiveData<Example> getMovieDetail(String movieId) {
        movieDetail = movieDetailRepo.getMovieDetail(movieId);
        return movieDetail;
    }

    public LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example> getMovieTrailor(String movieId) {
        movieTrailor = movieDetailRepo.getMovieTrailors(movieId);
        return movieTrailor;
    }

    public LiveData<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example> getMoveCrewCast(String movieId) {
        moveCrewCast = movieDetailRepo.getMovieCrewCast(movieId);
        return moveCrewCast;
    }

}
