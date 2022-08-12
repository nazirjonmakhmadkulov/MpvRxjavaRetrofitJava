package com.nazirjon.mpvrxjavaretrofitjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nazirjon.mpvrxjavaretrofitjava.R;
import com.nazirjon.mpvrxjavaretrofitjava.models.Result;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    List<Result> movieList;
    Context context;

    public MoviesAdapter(List<Result> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_movies, parent, false);
        return new MoviesHolder(v);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        holder.tvOverview.setText(movieList.get(position).getOverview());
        holder.tvReleaseDate.setText(movieList.get(position).getReleaseDate());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" +
                movieList.get(position).getPosterPath()).into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MoviesHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvOverview, tvReleaseDate;
        ImageView ivMovie;

        public MoviesHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvOverview = (TextView) v.findViewById(R.id.tvOverView);
            tvReleaseDate = (TextView) v.findViewById(R.id.tvReleaseDate);
            ivMovie = (ImageView) v.findViewById(R.id.ivMovie);
        }
    }
}
