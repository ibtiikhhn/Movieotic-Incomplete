package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.CreditTvClickListener;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreditTvAdapter extends RecyclerView.Adapter<CreditTvAdapter.CreditTvHolder> {

    public static final String TAG = "CreditTvAdapter";
    Context context;
    List<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Cast> tvList;

    CreditTvClickListener clickListener;

    public CreditTvAdapter(Context context, List<com.codeiatic.movieotic.Models.PersonModels.CreditTv.Cast> tvList, CreditTvClickListener clickListener) {
        this.clickListener = clickListener;
        this.context = context;
        this.tvList = tvList;
    }


    @NonNull
    @Override
    public CreditTvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.actor_movies, parent, false);
        CreditTvHolder creditTvHolder = new CreditTvHolder(view);
        return creditTvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreditTvHolder holder, int position) {
        com.codeiatic.movieotic.Models.PersonModels.CreditTv.Cast cast = tvList.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+cast.getPosterPath()).into(holder.moviePoster);
        holder.movieName.setText(cast.getName());

    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public class CreditTvHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        TextView movieName;

        public CreditTvHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
            moviePoster = itemView.findViewById(R.id.moviePoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        com.codeiatic.movieotic.Models.PersonModels.CreditTv.Cast cast = tvList.get(getAdapterPosition());
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onTvClick(cast.getId().toString());
                        }
                    }
                }
            });
        }
    }
}
