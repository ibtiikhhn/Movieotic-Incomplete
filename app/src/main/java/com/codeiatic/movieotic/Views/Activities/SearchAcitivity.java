package com.codeiatic.movieotic.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codeiatic.movieotic.Adapters.SearchMoviesAdapter;
import com.codeiatic.movieotic.ClickListeners.CreditMovieClickListener;
import com.codeiatic.movieotic.Models.Movies.Example;
import com.codeiatic.movieotic.Models.Movies.Result;
import com.codeiatic.movieotic.NetworkUtils.NetworkClient;
import com.codeiatic.movieotic.NetworkUtils.SearchMovies;
import com.codeiatic.movieotic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchAcitivity extends AppCompatActivity implements CreditMovieClickListener {

    public static final String TAG = "SearchActivity";

    RecyclerView recyclerView;
    SearchMoviesAdapter moviesAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Result> list;
    AppCompatImageButton backBT;
    AppCompatEditText searchET;
    Timer timer;
    ProgressBar progressBar;
    TextView noMovieTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_acitivity);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        backBT = findViewById(R.id.backBT);
        searchET = findViewById(R.id.searchET);
        progressBar = findViewById(R.id.searchPB);
        noMovieTV = findViewById(R.id.noMovieTV);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        noMovieTV.setVisibility(View.INVISIBLE);

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // user typed: start the timer
                final String query = s.toString();
                timer = new Timer();
                if (!query.isEmpty()) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // do your actual work here
                            SearchAcitivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);
                                    noMovieTV.setVisibility(View.INVISIBLE);
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    makeSearch(query);
                                }
                            });
                        }
                    }, 600); // 600ms delay before the timer executes the "run" method from TimerTask
                }

            }
        });
    }

    void makeSearch(String query) {
        Retrofit retrofit = NetworkClient.getRetrofit();
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<Example> call = searchMovies.getMoviesBasedOnQuery("381054badc0dabe1a386b89e1d169c2d", query);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (!list.isEmpty()) {
                    list = new ArrayList<>();
                }
                if (response.body() != null && response.body().getResults() != null) {
                    list.addAll(response.body().getResults());
                    moviesAdapter = new SearchMoviesAdapter(getApplicationContext(), list, SearchAcitivity.this);
                    recyclerView.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                    noMovieTV.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if (response.body().getResults().isEmpty()) {
                        noMovieTV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(String movieId) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }
}
