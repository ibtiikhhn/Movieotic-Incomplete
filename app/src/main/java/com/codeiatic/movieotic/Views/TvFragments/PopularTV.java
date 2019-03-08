package com.codeiatic.movieotic.Views.TvFragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.codeiatic.movieotic.Views.Activities.TvDetailActivity;

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
public class PopularTV extends Fragment implements CreditMovieClickListener {

    public static final String TAG = "TopRatedTV";
    public static PopularTV popularTV;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MoviesAdapter moviesAdapter;
    ProgressBar progressBar;
    Button retryButton;
    TextView errorTextView;
    MoviesViewModel moviesViewModel;

    public static PopularTV getInstance() {
        if (popularTV == null) {
            popularTV = new PopularTV();
        }
        return popularTV;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        moviesAdapter = new MoviesAdapter(getContext());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.setOnItemClickListener(this);
        progressBar.setVisibility(View.VISIBLE);
        moviesViewModel = ViewModelProviders.of(this.getActivity()).get(MoviesViewModel.class);

        moviesViewModel.getPopularTvPagedList().observe(this.getActivity(), new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                Log.i(TAG, "onChanged: "+results.isEmpty());
                moviesAdapter.submitList(results);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popular_tv, container, false);

        recyclerView = view.findViewById(R.id.popularTVRV);
        progressBar = view.findViewById(R.id.progressBarPTV);
        retryButton = view.findViewById(R.id.errorButtonPTV);
        errorTextView = view.findViewById(R.id.errorTextPTV);

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }


    public void setErrorsVisible() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
    }

    public void setErrorsInvisible() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onItemClick(String movieId) {
        Log.i(TAG, "onItemClick: "+movieId);
        Intent intent = new Intent(getActivity(), TvDetailActivity.class);
        intent.putExtra("tvId", movieId);
        startActivity(intent);
    }
}
