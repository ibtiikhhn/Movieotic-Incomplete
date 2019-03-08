package com.codeiatic.movieotic.ViewModels;

import android.app.Application;

import com.codeiatic.movieotic.Models.PersonModels.ExternalIds;
import com.codeiatic.movieotic.Models.PersonModels.People.Example;
import com.codeiatic.movieotic.Models.PersonModels.People.Person;
import com.codeiatic.movieotic.Repository.PersonRepo;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PersonViewModel extends AndroidViewModel {

    PersonRepo personRepo;

    LiveData<Person> personLiveData;
    LiveData<Example> personImages;
    LiveData<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> personMovies;
    LiveData<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example> personTv;
    LiveData<ExternalIds> externalIdsLiveData;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRepo = new PersonRepo();
    }

    public LiveData<ExternalIds> getExternalIdsLiveData(String personId) {
        externalIdsLiveData = personRepo.getExternalIds(personId);
        return externalIdsLiveData;
    }

    public LiveData<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example> getPersonTv(String personId) {
        personTv = personRepo.getTvCredits(personId);
        return personTv;
    }

    public LiveData<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example> getPersonMovies(String personId) {
        personMovies = personRepo.getMovieCredits(personId);
        return personMovies;
    }

    public LiveData<Person> getPersonLiveData(String personId) {
        personLiveData = personRepo.getPersonDetail(personId);
        return personLiveData;
    }

    public LiveData<Example> getPersonImages(String personId) {
        personImages = personRepo.getPersonImages(personId);
        return personImages;
    }
}
