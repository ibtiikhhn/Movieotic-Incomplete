package com.codeiatic.movieotic.DataSources;

import com.codeiatic.movieotic.Models.Movies.Result;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class TopRatedMoviesDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Result>> mutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        TopRatedMoviesDataSource topRatedMoviesDataSource = new TopRatedMoviesDataSource();
        mutableLiveData.postValue(topRatedMoviesDataSource);
        return topRatedMoviesDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Result>> getMutableLiveData() {
        return mutableLiveData;
    }
}
