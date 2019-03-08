package com.codeiatic.movieotic.Views.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.Adapters.CreditMoviesAdapter;
import com.codeiatic.movieotic.Adapters.CreditTvAdapter;
import com.codeiatic.movieotic.ClickListeners.CreditMovieClickListener;
import com.codeiatic.movieotic.ClickListeners.CreditTvClickListener;
import com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Cast;
import com.codeiatic.movieotic.Models.PersonModels.ExternalIds;
import com.codeiatic.movieotic.Models.PersonModels.People.Example;
import com.codeiatic.movieotic.Models.PersonModels.People.Person;
import com.codeiatic.movieotic.Models.PersonModels.People.Profile;
import com.codeiatic.movieotic.R;
import com.codeiatic.movieotic.ViewModels.PersonViewModel;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileActivity extends AppCompatActivity implements CreditMovieClickListener, CreditTvClickListener {

    public static final String TAG = "ProfileActivity";

    ImageView imageView;
    TextView name;
    TextView dob;
    TextView location;
    ExpandableTextView bio;
    TextView knownFor;
    SliderLayout sliderLayout;
    RecyclerView moviesRV;
    CreditMoviesAdapter moviesAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Cast> movies;

    Toolbar toolbar;
    Map<String,String> hashMapForURL;

    CardView socialCV;
    CardView bioCV;
    CardView moviesCV;
    CardView tvCV;
    List<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Cast> tvShows;
    LinearLayoutManager linearLayoutManager1;
    RecyclerView tvRV;
    CreditTvAdapter tvAdapter;
    ProgressBar progressBar;

    ImageView fbIV;
    ImageView instaIV;
    ImageView twitterIV;
    ImageView imdbIV;

    String fbId = "www.facebook.com/", instaId="www.instagram.com/", twitterId="www.twitter.com/", imdbIc="imdb.com/name/";
    PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        fbIV = findViewById(R.id.fbIV);
        instaIV = findViewById(R.id.instaIV);
        twitterIV = findViewById(R.id.twitterIV);
        imdbIV = findViewById(R.id.imdbIV);

        socialCV = findViewById(R.id.socialCV);
        bioCV = findViewById(R.id.infoCV);
        moviesCV = findViewById(R.id.moviesCV);

        socialCV.setVisibility(View.INVISIBLE);
        bioCV.setVisibility(View.INVISIBLE);
        moviesCV.setVisibility(View.INVISIBLE);

        tvCV = findViewById(R.id.tvCV);
        tvCV.setVisibility(View.INVISIBLE);
        tvShows = new ArrayList<>();
        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tvRV = findViewById(R.id.tvRV);
        tvAdapter = new CreditTvAdapter(this, tvShows,this);
        tvRV.setLayoutManager(linearLayoutManager1);
        tvRV.setAdapter(tvAdapter);
        progressBar = findViewById(R.id.myPB);
        progressBar.setVisibility(View.VISIBLE);
        imageView = findViewById(R.id.personImageView);
        name = findViewById(R.id.personName);
        dob = findViewById(R.id.personDOB);
        location = findViewById(R.id.location);
        bio = findViewById(R.id.personBio);
        moviesRV = findViewById(R.id.moviesRV);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        moviesRV.setLayoutManager(linearLayoutManager);
        movies = new ArrayList<>();
        moviesAdapter = new CreditMoviesAdapter(this, movies,this);
        moviesRV.setAdapter(moviesAdapter);
        knownFor = findViewById(R.id.knownFor);
        hashMapForURL = new HashMap<>();

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);

        sliderLayout = findViewById(R.id.tvImageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        Intent intent = getIntent();
        final String personId = intent.getStringExtra("personId");
        Log.i(TAG, "onCreate: " + personId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setExternalIdsClickListeners();

        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personViewModel.getPersonLiveData(personId).observe(this, new Observer<Person>() {
            @Override
            public void onChanged(Person person) {
                if (person != null) {
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/" + person.getProfilePath()).into(imageView);
                    name.setText(person.getName());
                    toolbar.setTitle(person.getName());
                    dob.setText(person.getBirthday());
                    location.setText(person.getPlaceOfBirth());
                    bio.setText(person.getBiography());
                    knownFor.setText(person.getKnownForDepartment());
                    bioCV.setVisibility(View.VISIBLE);
                    socialCV.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i(TAG, "onChanged: "+person.getBiography());
                }

            }
        });

        personViewModel.getPersonImages(personId).observe(this, new Observer<Example>() {
            @Override
            public void onChanged(Example example) {
                if (example != null) {
                    setSliderViews(example.getProfiles());
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Failed to show images" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        personViewModel.getPersonMovies(personId).observe(this, new Observer<com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Example example) {
                movies.addAll(example.getCast());
                moviesAdapter.notifyDataSetChanged();
                moviesCV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        personViewModel.getPersonTv(personId).observe(this, new Observer<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.PersonModels.CreditTv.Example example) {
                tvShows.addAll(example.getCast());
                tvAdapter.notifyDataSetChanged();
                tvCV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        personViewModel.getExternalIdsLiveData(personId).observe(this, new Observer<ExternalIds>() {
            @Override
            public void onChanged(ExternalIds externalIds) {
                if (externalIds.getFacebookId() == null) {
                    fbId = "";
                } else {
                    fbId += externalIds.getFacebookId();
                }
                if (externalIds.getTwitterId() == null) {
                    twitterId = "";
                }
                else{
                    twitterId += externalIds.getTwitterId();

                }
                if (externalIds.getInstagramId() == null) {
                    instaId = "";
                }
                else{
                    instaId += externalIds.getInstagramId();
                }
                if (externalIds.getImdbId() == null) {
                    imdbIc = "";
                }
                else{
                    imdbIc += externalIds.getImdbId();
                }



            }
        });
    }

    private void setSliderViews(List<Profile> profiles) {

        for (int i = 0; i < profiles.size(); i++) {

            SliderView sliderView = new SliderView(this);

            sliderView.setImageUrl("https://image.tmdb.org/t/p/w500/" + profiles.get(i).getFilePath());
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(ProfileActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onItemClick(String movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTvClick(String tvId) {
        Intent intent = new Intent(this, TvDetailActivity.class);
        intent.putExtra("tvId", tvId);
        startActivity(intent);
    }

    void setExternalIdsClickListeners() {

        fbIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fbId.isEmpty()) {
                    sendIdIntentToBrowser(fbId);
                    return;
                }
                showErrorToast();
            }
        });

        twitterIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!twitterId.isEmpty()) {
                    sendIdIntentToBrowser(twitterId);
                    return;
                }
                showErrorToast();
            }
        });

        instaIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instaId.isEmpty()) {
                    sendIdIntentToBrowser(instaId);
                    return;
                }
                showErrorToast();
            }
        });

        imdbIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imdbIc.isEmpty()) {
                    sendIdIntentToBrowser(imdbIc);
                    return;
                }
                showErrorToast();
            }
        });
    }

    public void showErrorToast() {
        Toast.makeText(this, "Sorry, Not Available..", Toast.LENGTH_SHORT).show();
    }

    void sendIdIntentToBrowser(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")){
            url = "http://" + url;
        }
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(openUrlIntent);
    }
}
