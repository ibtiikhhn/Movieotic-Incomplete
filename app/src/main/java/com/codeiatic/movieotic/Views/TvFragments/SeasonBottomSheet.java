package com.codeiatic.movieotic.Views.TvFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.Models.TvDetailModel.Season;
import com.codeiatic.movieotic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SeasonBottomSheet extends BottomSheetDialogFragment {

    Season season;

    TextView seasonName;
    TextView episodes;
    TextView details;
    TextView airDate;
    ImageView seasonPoster;

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.season_bottom_sheet, container, false);
        seasonName = view.findViewById(R.id.seasonName);
        episodes = view.findViewById(R.id.seasonEpisodes);
        details = view.findViewById(R.id.episodeDetails);
        airDate = view.findViewById(R.id.seasonAirDate);
        seasonPoster = view.findViewById(R.id.episodeImage);
        if (season != null) {
            setViews();
        }
        return view;
    }

    public void setSeason(Season season, Context context) {
        this.context = context;
        this.season = season;
    }

    public void setViews() {
        seasonName.setText(season.getName());
        episodes.setText(String.valueOf(season.getEpisodeCount()));
        details.setText(season.getOverview());
        airDate.setText(season.getAirDate());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" +season.getPosterPath()).into(seasonPoster);
    }
}
