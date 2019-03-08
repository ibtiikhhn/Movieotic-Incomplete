package com.codeiatic.movieotic.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.Adapters.CastAdapter;
import com.codeiatic.movieotic.Adapters.CrewAdapter;
import com.codeiatic.movieotic.Adapters.SeasonsAdapter;
import com.codeiatic.movieotic.ClickListeners.CastClickListener;
import com.codeiatic.movieotic.ClickListeners.CrewClickListener;
import com.codeiatic.movieotic.ClickListeners.SeasonClickListener;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Cast;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Crew;
import com.codeiatic.movieotic.Models.TvDetailModel.Example;
import com.codeiatic.movieotic.Models.TvDetailModel.Season;
import com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Backdrop;
import com.codeiatic.movieotic.R;
import com.codeiatic.movieotic.ViewModels.DetailTvViewModel;
import com.codeiatic.movieotic.Views.TvFragments.SeasonBottomSheet;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TvDetailActivity extends AppCompatActivity implements SeasonClickListener, CastClickListener, CrewClickListener {

    public static final String TAG = "TvDetailActivity";
    String tvId;
    DetailTvViewModel viewModel;

    SliderLayout sliderLayout;
    RecyclerView recyclerView;
    SeasonsAdapter seasonsAdapter;
    LinearLayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager1;
    LinearLayoutManager linearLayoutManager2;
    List<Season> seasons;
    ImageView tvPoster;
    TextView tvName;
    TextView tvGenre;
    TextView tvReleaseDate;
    TextView tvDescription;
    RecyclerView castRV;
    RecyclerView crewRV;
    CastAdapter castAdapter;
    CrewAdapter crewAdapter;
    List<Cast> castList;
    List<Crew> crewList;
    CardView detailCV;
    CardView castCV;
    CardView crewCV;
    CardView seasonsCV;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);


        initViews();
        Intent intent = getIntent();
        tvId = intent.getStringExtra("tvId");
        Log.i(TAG, "onCreate: " + tvId);

        viewModel = ViewModelProviders.of(this).get(DetailTvViewModel.class);


        viewModel.getTvImages(tvId).observe(this, new Observer<com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.TvDetailModel.TvImages.Example example) {
                if (example != null) {
                    setSliderViews(example.getBackdrops());
                }
            }
        });

        viewModel.getTvCastCrew(tvId).observe(this, new Observer<com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example>() {
            @Override
            public void onChanged(com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Example example) {
                if (example != null) {
                    castList.addAll(example.getCast());
                    crewList.addAll(example.getCrew());
                    castAdapter.notifyDataSetChanged();
                    crewAdapter.notifyDataSetChanged();
                    castCV.setVisibility(View.VISIBLE);
                    crewCV.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getTvDetails(tvId).observe(this, new Observer<Example>() {
            @Override
            public void onChanged(Example example) {
                Log.i(TAG, "Its here onChanged: "+example);
                if (example != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    detailCV.setVisibility(View.VISIBLE);
                    seasonsCV.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/" + example.getPosterPath()).into(tvPoster);
                    tvName.setText(example.getName());
                    toolbar.setTitle(example.getName());
                    if (!example.getGenres().isEmpty()) {
                        tvGenre.setText(example.getGenres().get(0).getName());
                    } else {
                        tvGenre.setText("N/A");
                    }
                    tvReleaseDate.setText(example.getFirstAirDate());
                    tvDescription.setText(example.getOverview());
                    seasons.addAll(example.getSeasons());
                    seasonsAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onSeasonClick(int position) {
        SeasonBottomSheet bottomSheet = new SeasonBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), "EEEh");
        bottomSheet.setSeason(seasons.get(position), getApplicationContext());
    }

    public void setSliderViews(List<Backdrop> backdrops) {

        for (int i = 0; i < backdrops.size(); i++) {

            SliderView sliderView = new SliderView(this);

            sliderView.setImageUrl("https://image.tmdb.org/t/p/w500/" + backdrops.get(i).getFilePath());
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(ProfileActivity.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onCastClick(int position) {
        Integer id = castList.get(position).getId();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("personId", String.valueOf(id));
        Log.i(TAG, "onClick: " + String.valueOf(id));
        startActivity(intent);
    }

    @Override
    public void onCrewClick(int position) {
        Integer id = crewList.get(position).getId();
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("personId", String.valueOf(id));
        Log.i(TAG, "onClick: " + String.valueOf(id));
        startActivity(intent);
    }


    public void initViews() {
        toolbar = findViewById(R.id.tvToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sliderLayout = findViewById(R.id.tvImageSlider);
        recyclerView = findViewById(R.id.seasonsRv);
        tvPoster = findViewById(R.id.tvPoster);
        tvName = findViewById(R.id.tvNameTv);
        tvGenre = findViewById(R.id.tvGenre);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvDescription = findViewById(R.id.tvDescription);
        seasons = new ArrayList<>();
        seasonsAdapter = new SeasonsAdapter(seasons, getApplicationContext());
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        seasonsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(seasonsAdapter);

        castRV = findViewById(R.id.tvCastRV);
        crewRV = findViewById(R.id.tvCrewRv);
        castRV.setLayoutManager(linearLayoutManager1);
        crewRV.setLayoutManager(linearLayoutManager2);
        castList = new ArrayList<>();
        crewList = new ArrayList<>();
        castAdapter = new CastAdapter(castList, getApplicationContext());
        crewAdapter = new CrewAdapter(crewList, getApplicationContext());
        crewAdapter.setOnItemClickListener(this);
        castAdapter.setOnItemClickListener(this);
        castRV.setAdapter(castAdapter);
        crewRV.setAdapter(crewAdapter);

        castCV = findViewById(R.id.tvCastCV);
        crewCV = findViewById(R.id.tvCrewCV);
        detailCV = findViewById(R.id.cardDetail);
        seasonsCV = findViewById(R.id.moviesCV);
        castCV.setVisibility(View.INVISIBLE);
        crewCV.setVisibility(View.INVISIBLE);
        detailCV.setVisibility(View.INVISIBLE);
        seasonsCV.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.myPB);
        progressBar.setVisibility(View.VISIBLE);

    }
}
