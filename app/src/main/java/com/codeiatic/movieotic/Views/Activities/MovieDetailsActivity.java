package com.codeiatic.movieotic.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.Adapters.CastAdapter;
import com.codeiatic.movieotic.ClickListeners.CastClickListener;
import com.codeiatic.movieotic.Adapters.CrewAdapter;
import com.codeiatic.movieotic.ClickListeners.CrewClickListener;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Cast;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Crew;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example;
import com.codeiatic.movieotic.R;
import com.codeiatic.movieotic.ViewModels.DetailMoviesViewModel;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieDetailsActivity extends AppCompatActivity implements CastClickListener, CrewClickListener {

    public static final String TAG = "DetailActivity";
    ImageView moviePosterIv;
    TextView movieNameTV;
    TextView genreTv;
    TextView releaseDateTv;
    TextView movieDescription;
    TextView genreTVStatic;
    TextView releaseDateTVStatic;
    TextView castStatic;
    TextView crewStatic;
    ProgressBar progressBar;
    YouTubePlayerView youTubePlayerView;
    CardView detailCV;

    Toolbar toolbar;


    RecyclerView crewRv;
    RecyclerView castRv;
    List<Crew> crewList;
    List<Cast> castList;
    List<Example> exampleList;
    LinearLayoutManager castLayoutManager;
    LinearLayoutManager crewLayoutManager;

    Intent movieIdIntent;
    String movieId;

    DetailMoviesViewModel detailMoviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        initializeViews();
        showProgressHideViews();
        movieIdIntent = getIntent();
        movieId = movieIdIntent.getStringExtra("movieId");

        getLifecycle().addObserver(youTubePlayerView);
        castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        castRv.setLayoutManager(castLayoutManager);
        crewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        crewRv.setLayoutManager(crewLayoutManager);

        Log.i(TAG, "onCreate: "+movieId);

        crewList = new ArrayList<>();
        castList = new ArrayList<>();
        exampleList = new ArrayList<>();

        detailMoviesViewModel = ViewModelProviders.of(this).get(DetailMoviesViewModel.class);
        detailMoviesViewModel.getMovieDetail(movieId).observe(this, new Observer<com.codeiatic.movieotic.Models.MovieDetailModel.MovieModel.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.MovieDetailModel.MovieModel.Example example) {
                Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/" + example.getPosterPath()).into(moviePosterIv);
                String genres = null;
                for (int i = 0; i < example.getGenres().size(); i++) {
                    if (i == 0) {
                        genres = example.getGenres().get(i).getName();
                    }else {
                        genres = genres + ", " + example.getGenres().get(i).getName();
                    }
                }

                genreTv.setText(genres);
                movieNameTV.setText(example.getOriginalTitle());
                releaseDateTv.setText(example.getReleaseDate());
                movieDescription.setText(example.getOverview());
                hideProgressShowViews();
            }
        });

        detailMoviesViewModel.getMoveCrewCast(movieId).observe(this, new Observer<Example>() {
            @Override
            public void onChanged(Example example) {
                if (example!= null) {
                    castList.addAll(example.getCast());
                    crewList.addAll(example.getCrew());
                    CastAdapter castAdapter = new CastAdapter(castList, getApplicationContext());
                    CrewAdapter crewAdapter = new CrewAdapter(crewList, getApplicationContext());
                    castAdapter.setOnItemClickListener(MovieDetailsActivity.this);
                    crewAdapter.setOnItemClickListener(MovieDetailsActivity.this);
                    crewRv.setAdapter(crewAdapter);
                    castRv.setAdapter(castAdapter);
                }
            }
        });

        detailMoviesViewModel.getMovieTrailor(movieId).observe(this, new Observer<com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.MovieDetailModel.Trailor.Example example) {
                if (example != null) {
                    if (example.getResults().size() > 0) {
                        initYoutube(example.getResults().get(0).getKey());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Trailor Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initYoutube(final String trailorId) {
        youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.cueVideo(trailorId, 0);
                        initializedYouTubePlayer.play();
                    }

                    @Override
                    public void onError(@NonNull PlayerConstants.PlayerError error) {
                        super.onError(error);
                        initializedYouTubePlayer.pause();
                    }
                });
            }
        }, true);
    }

    public void initializeViews() {
        detailCV = findViewById(R.id.cardDetail);
        releaseDateTv = findViewById(R.id.releaseDateTv);
        castRv = findViewById(R.id.castCardRv);
        crewRv = findViewById(R.id.crewCardRv);
        crewStatic = findViewById(R.id.crewTvStatic);
        movieNameTV = findViewById(R.id.movieNameTextView);
        genreTv = findViewById(R.id.genreTv);
        moviePosterIv = findViewById(R.id.posterIv);
        movieDescription = findViewById(R.id.movieDescription);
        releaseDateTVStatic = findViewById(R.id.releaseDateTVStatic);
        genreTVStatic = findViewById(R.id.genreStaticTv);
        progressBar = findViewById(R.id.detailMoviePb);
        castStatic = findViewById(R.id.castTvStatic);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void showProgressHideViews() {
        progressBar.setVisibility(View.VISIBLE);
        crewStatic.setVisibility(View.INVISIBLE);
        castStatic.setVisibility(View.INVISIBLE);
        detailCV.setVisibility(View.INVISIBLE);
    }

    public void hideProgressShowViews() {
        progressBar.setVisibility(View.INVISIBLE);
        castStatic.setVisibility(View.VISIBLE);
        crewStatic.setVisibility(View.VISIBLE);
        detailCV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onCastClick(int position) {
        Integer id = castList.get(position).getId();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("personId", String.valueOf(id));
        Log.i(TAG, "onClick: "+String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public void onCrewClick(int position) {
        Integer id = crewList.get(position).getId();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("personId", String.valueOf(id));
        Log.i(TAG, "onClick: "+String.valueOf(id));
        startActivity(intent);
    }
}
