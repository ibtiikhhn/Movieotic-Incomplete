package com.codeiatic.movieotic.Views.MoviesFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codeiatic.movieotic.Adapters.MoviesAdapter;
import com.codeiatic.movieotic.ClickListeners.CreditMovieClickListener;
import com.codeiatic.movieotic.Models.Movies.Result;
import com.codeiatic.movieotic.R;
import com.codeiatic.movieotic.ViewModels.MoviesViewModel;
import com.codeiatic.movieotic.Views.Activities.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedMovies extends Fragment implements CreditMovieClickListener {

    MoviesViewModel moviesViewModel;
    static TopRatedMovies topRatedMovies;

    RecyclerView moviesRv;
    MoviesAdapter moviesAdapter;
    List<Result> arrayList;
    LinearLayoutManager mLayoutManager;

    ProgressBar progressBar;
    Button retryButton;
    TextView errorTextView;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    int totalPages = 10;
    int page = 1;
    private boolean loading = true;

    public static TopRatedMovies getInstance() {
        if (topRatedMovies == null) {
            topRatedMovies = new TopRatedMovies();
        }
        return topRatedMovies;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        moviesRv.setLayoutManager(mLayoutManager);
        arrayList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getContext());
        moviesRv.setAdapter(moviesAdapter);
        moviesAdapter.setOnItemClickListener(this);
        moviesViewModel = ViewModelProviders.of(this.getActivity()).get(MoviesViewModel.class);
        moviesViewModel.getTopRatedMoviesPagedList().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                moviesAdapter.submitList(results);
                progressBar.setVisibility(View.INVISIBLE);
                moviesRv.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated_movies, container, false);
        moviesRv = view.findViewById(R.id.topRatedMoviesRV);
        progressBar = view.findViewById(R.id.progressBarTRM);
        retryButton = view.findViewById(R.id.errorButtonTRM);
        errorTextView = view.findViewById(R.id.errorTextTRM);

        progressBar.setVisibility(View.VISIBLE);
        moviesRv.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                moviesRv.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.INVISIBLE);
                }
        });
        return view;
    }


    public void setErrorsVisible() {
        moviesRv.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    public void setErrorsInvisible() {
        moviesRv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(String movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }
}
