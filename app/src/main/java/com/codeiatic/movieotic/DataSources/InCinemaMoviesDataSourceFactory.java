package com.codeiatic.movieotic.DataSources;

import com.codeiatic.movieotic.Models.Movies.Result;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class InCinemaMoviesDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Result>> mutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        InCinemaMoviesDataSource inCinemaMoviesDataSource = new InCinemaMoviesDataSource();
        mutableLiveData.postValue(inCinemaMoviesDataSource);
        return inCinemaMoviesDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Result>> getMutableLiveData() {
        return mutableLiveData;
    }
}