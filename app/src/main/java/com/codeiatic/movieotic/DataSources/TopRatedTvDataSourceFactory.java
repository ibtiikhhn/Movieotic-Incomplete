package com.codeiatic.movieotic.DataSources;

import com.codeiatic.movieotic.Models.Movies.Result;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class TopRatedTvDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Result>> mutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource create() {
        TopRatedTvDataSource topRatedTvDataSource = new TopRatedTvDataSource();
        mutableLiveData.postValue(topRatedTvDataSource);
        return topRatedTvDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Result>> getMutableLiveData() {
        return mutableLiveData;
    }
}
