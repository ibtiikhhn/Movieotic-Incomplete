package com.codeiatic.movieotic.Repository;

import com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example;
import com.codeiatic.movieotic.Models.PersonModels.ExternalIds;
import com.codeiatic.movieotic.Models.PersonModels.People.Person;
import com.codeiatic.movieotic.NetworkUtils.NetworkClient;
import com.codeiatic.movieotic.NetworkUtils.PersonDetail;
import com.codeiatic.movieotic.NetworkUtils.PersonExternalIds;
import com.codeiatic.movieotic.NetworkUtils.PersonImages;
import com.codeiatic.movieotic.NetworkUtils.PersonMovieCredits;
import com.codeiatic.movieotic.NetworkUtils.PersonTvCredits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PersonRepo {

    Retrofit retrofit;
    public PersonRepo() {
        retrofit = NetworkClient.getRetrofit();
    }

    public LiveData<Example> getTvCredits(String personId) {
        PersonTvCredits tvCredits = retrofit.create(PersonTvCredits.class);
        final MutableLiveData liveData = new MutableLiveData();
        tvCredits.getTvCredits(personId,"381054badc0dabe1a386b89e1d169c2d").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<Person> getPersonDetail(String personId) {
        PersonDetail personDetail = retrofit.create(PersonDetail.class);
        Call<Person> call = personDetail.getPersonDetail(personId, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData mutableLiveData = new MutableLiveData();
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
                mutableLiveData.setValue(null);

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<com.codeiatic.movieotic.Models.PersonModels.People.Example> getPersonImages(String personId) {
        PersonImages images = retrofit.create(PersonImages.class);
        Call<com.codeiatic.movieotic.Models.PersonModels.People.Example> call = images.getPersonImages(personId, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData mutableLiveData = new MutableLiveData();
        call.enqueue(new Callback<com.codeiatic.movieotic.Models.PersonModels.People.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.PersonModels.People.Example> call, Response<com.codeiatic.movieotic.Models.PersonModels.People.Example> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.PersonModels.People.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }



    public LiveData<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> getMovieCredits(String personId) {
        PersonMovieCredits credits = retrofit.create(PersonMovieCredits.class);
        Call<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> call = credits.getMovieCredits(personId, "381054badc0dabe1a386b89e1d169c2d");
        final MutableLiveData mutableLiveData = new MutableLiveData();
        call.enqueue(new Callback<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example>() {
            @Override
            public void onResponse(Call<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> call, Response<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> response) {

                if (response.body() != null) {
                    mutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<ExternalIds> getExternalIds(String personId) {
        PersonExternalIds externalIds = retrofit.create(PersonExternalIds.class);
        final MutableLiveData liveData = new MutableLiveData();
        Call<ExternalIds> call = externalIds.getExternalIds(personId, "381054badc0dabe1a386b89e1d169c2d");
        call.enqueue(new Callback<ExternalIds>() {
            @Override
            public void onResponse(Call<ExternalIds> call, Response<ExternalIds> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ExternalIds> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
