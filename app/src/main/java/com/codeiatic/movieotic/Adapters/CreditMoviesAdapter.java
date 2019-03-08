package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.CreditMovieClickListener;
import com.codeiatic.movieotic.Models.PersonModels.CreditMovies.Cast;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreditMoviesAdapter extends RecyclerView.Adapter<CreditMoviesAdapter.CreditMoviesHolder> {

    Context context;
    List<Cast> movies;

    CreditMovieClickListener clickListener;

    public CreditMoviesAdapter(Context context, List<Cast> movies, CreditMovieClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @Override
    public CreditMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.actor_movies, parent, false);
        CreditMoviesHolder creditMoviesHolder = new CreditMoviesHolder(view);
        return creditMoviesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreditMoviesHolder holder, int position) {
        Cast cast = movies.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+cast.getPosterPath()).into(holder.moviePoster);
        holder.movieName.setText(cast.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class CreditMoviesHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieName;

        public CreditMoviesHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            moviePoster = itemView.findViewById(R.id.moviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        Cast cast = movies.get(getAdapterPosition());
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(cast.getId().toString());
                        }
                    }
                }
            });
        }
    }
}

