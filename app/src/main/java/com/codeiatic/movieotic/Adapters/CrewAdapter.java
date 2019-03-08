package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.CrewClickListener;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Crew;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewHolder> {

    List<Crew> crewList;
    Context context;

    CrewClickListener clickListener;

    public void setOnItemClickListener(CrewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CrewAdapter(List<Crew> crewList, Context context) {
        this.crewList = crewList;
        this.context = context;
    }

    @NonNull
    @Override
    public CrewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cast_crew_cardview, parent, false);
        final CrewHolder crewHolder = new CrewHolder(v);
        return crewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CrewHolder holder, int position) {
        holder.crewName.setText(crewList.get(position).getName().toString());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + crewList.get(position).getProfilePath()).into(holder.crewImage);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public class CrewHolder extends RecyclerView.ViewHolder {

        ImageView crewImage;
        TextView crewName;

        public CrewHolder(View itemView) {
            super(itemView);
            crewImage = itemView.findViewById(R.id.castCrewImage);
            crewName = itemView.findViewById(R.id.castCrewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onCrewClick(position);
                        }
                    }
                }
            });
        }
    }
}
