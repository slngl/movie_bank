package com.slngl.moviebank.view.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.slngl.moviebank.base.AppConstants;
import com.slngl.moviebank.databinding.ItemHomeMovieBinding;
import com.slngl.moviebank.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeMoviesAdapter extends RecyclerView.Adapter<HomeMoviesAdapter.HomeMoviesViewHolder> {

    private ArrayList<Movie> movieList;
    private ItemHomeMovieBinding binding;
    private final Context context;

    public HomeMoviesAdapter(ArrayList<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    public void setMovieList(ArrayList<Movie> movieList){
        this.movieList=movieList;
        notifyDataSetChanged();
    }

    static class HomeMoviesViewHolder extends RecyclerView.ViewHolder{

        private final ItemHomeMovieBinding binding;

        public HomeMoviesViewHolder(ItemHomeMovieBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    @NonNull
    @NotNull
    @Override
    public HomeMoviesAdapter.HomeMoviesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemHomeMovieBinding.inflate(inflater, parent, false);
        return new HomeMoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeMoviesAdapter.HomeMoviesViewHolder holder, int position) {

        Movie item = movieList.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            holder.binding.movieItemRelativeLayout.setClipToOutline(true);
        }
        holder.binding.movieItemName.setText(item.getTitle());
        holder.binding.movieItemVotes.setText(item.getVote_count().toString());

        Glide.with(context).load(AppConstants.ImageBaseURLw500+item.getPoster_path())
                .into(holder.binding.movieItemImage);

        holder.binding.movieItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate movieDetails
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }
}
