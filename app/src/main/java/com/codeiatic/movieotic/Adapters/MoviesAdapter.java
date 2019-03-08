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

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends PagedListAdapter<Result, MoviesAdapter.MoviesHolder> {

    Context context;

    CreditMovieClickListener mListener;

    public void setOnItemClickListener(CreditMovieClickListener listener) {
        mListener = listener;
    }

    public MoviesAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_movies, parent, false);
        MoviesHolder moviesHolder = new MoviesHolder(v);
        return moviesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        Result result = getItem(position);
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

    private static DiffUtil.ItemCallback<Result> diffCallback = new DiffUtil.ItemCallback<Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Result oldItem, @NonNull Result newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class MoviesHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView releaseDate;
        TextView rating;
        TextView description;
        ImageView poster;
        RatingBar ratingBar;

        public MoviesHolder(View itemView) {
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
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        Result result = getItem(position);
                        if (position != RecyclerView.NO_POSITION) {
                            if (result != null) {
                                mListener.onItemClick(String.valueOf(result.getId()));
                            }
                        }
                    }
                }
            });
        }
    }
}
