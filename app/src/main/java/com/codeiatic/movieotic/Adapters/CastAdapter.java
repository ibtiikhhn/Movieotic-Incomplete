package com.codeiatic.movieotic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codeiatic.movieotic.ClickListeners.CastClickListener;
import com.codeiatic.movieotic.Models.MovieDetailModel.CrewCast.Cast;
import com.codeiatic.movieotic.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    List<Cast> castList;
    Context context;

    CastClickListener clickListener;
    public void setOnItemClickListener(CastClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CastAdapter(List<Cast> castList, Context context) {
        this.castList = castList;
        this.context = context;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cast_crew_cardview, parent, false);
        final CastHolder castHolder = new CastHolder(v);
        return castHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {
        holder.castName.setText(castList.get(position).getName().toString());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + castList.get(position).getProfilePath()).into(holder.castImage);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastHolder extends RecyclerView.ViewHolder{

        ImageView castImage;
        TextView castName;

        public CastHolder(View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.castCrewImage);
            castName = itemView.findViewById(R.id.castCrewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListener.onCastClick(position);
                        }
                    }
                }
            });
        }
    }
}
