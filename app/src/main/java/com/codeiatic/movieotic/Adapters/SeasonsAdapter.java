package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.SeasonClickListener;
import com.codeiatic.movieotic.Models.TvDetailModel.Season;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.SeasonsHolder> {

    Context context;
    List<Season> seasons;

    SeasonClickListener seasonClickListener;

    public SeasonsAdapter(List<Season> seasons, Context context) {
        this.seasons = seasons;
        this.context = context;
    }

    public void setOnItemClickListener(SeasonClickListener seasonClickListener) {
        this.seasonClickListener = seasonClickListener;
    }

    @NonNull
    @Override
    public SeasonsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.seasons_card, parent, false);
        final SeasonsHolder seasonsHolder = new SeasonsHolder(v);
        return seasonsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsHolder holder, int position) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+seasons.get(position).getPosterPath()).into(holder.seasonPoster);
        holder.seasonNo.setText("Season "+seasons.get(position).getSeasonNumber());
    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }

    public class SeasonsHolder extends RecyclerView.ViewHolder {

        ImageView seasonPoster;
        TextView seasonNo;

        public SeasonsHolder(View itemView) {
            super(itemView);
            seasonPoster = itemView.findViewById(R.id.seasonPoster);
            seasonNo = itemView.findViewById(R.id.seasonNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (seasonClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            seasonClickListener.onSeasonClick(position);
                        }
                    }
                }
            });
        }
    }
}
