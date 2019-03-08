package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.CreditMovieClickListener;
import com.codeiatic.movieotic.Models.Movies.Result;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchMoviesAdapter extends RecyclerView.Adapter<SearchMoviesAdapter.SearchMoviesHolder> {
    Context context;
    List<Result> list;
    CreditMovieClickListener clickListener;

    public SearchMoviesAdapter(Context context, List<Result> list, CreditMovieClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movies, parent, false);
        SearchMoviesHolder moviesHolder = new SearchMoviesHolder(v);
        return moviesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMoviesHolder holder, int position) {
        Result result = list.get(position);
        if (result.getTitle() == null) {
            holder.title.setText(result.getName());
        } else {
            holder.title.setText(result.getTitle());
        }
        holder.description.setText(result.getOverview());
        holder.rating.setText("Avg : " + result.getVoteAverage().toString());

        if (result.getReleaseDate() == null) {
            holder.releaseDate.setText(result.getFirstAirDate());
        } else {
            holder.releaseDate.setText(result.getReleaseDate());
        }

        double d = result.getVoteAverage();
        float f = (float) d;
        holder.ratingBar.setRating(f);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchMoviesHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView releaseDate;
        TextView rating;
        TextView description;
        ImageView poster;
        RatingBar ratingBar;

        public SearchMoviesHolder(View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            title = itemView.findViewById(R.id.MovieTitleTv);
            releaseDate = itemView.findViewById(R.id.MovieReleaseDateTv);
            rating = itemView.findViewById(R.id.MovieRatingTv);
            description = itemView.findViewById(R.id.MovieDetailsTv);
            poster = itemView.findViewById(R.id.MovieImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        Result result = list.get(position);
                        if (position != RecyclerView.NO_POSITION) {
                            if (result != null) {
                                clickListener.onItemClick(String.valueOf(result.getId()));
                            }
                        }
                    }
                }
            });
        }
    }
}
