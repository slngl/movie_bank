package com.slngl.moviebank.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.slngl.moviebank.base.AppConstants;
import com.slngl.moviebank.databinding.FragmentMovieDetailBinding;
import com.slngl.moviebank.model.Cast;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.view.adapters.CastAdapter;
import com.slngl.moviebank.viewModel.MovieDetailsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieDetailsFragment extends Fragment {

    private MovieDetailsViewModel viewModel;
    private FragmentMovieDetailBinding binding;
    private Integer movieId;
    private CastAdapter castAdapter;
    private Movie mMovie;
    private ArrayList<Cast> castList;
    private int hour = 0, min = 0;
    private String genre, videoId;


    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(MovieDetailsFragment.this).get(MovieDetailsViewModel.class);
        castList = new ArrayList<Cast>();
        MovieDetailsFragmentArgs args = MovieDetailsFragmentArgs.fromBundle(getArguments());
        movieId = args.getMovieId();

        //get data from vm
        viewModel.getMovieDetails(movieId);
        viewModel.getCasts(movieId);

        observeData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.moviePoster.setClipToOutline(true);
        }
        castAdapter = new CastAdapter(getContext(), castList);
        binding.castRecyclerView.setAdapter(castAdapter);

        binding.playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if videoId not null, start to video dialog
            }
        });

    }

    public void observeData() {
        viewModel.getMovieDetails().observe(getViewLifecycleOwner(), new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                mMovie = movie;
                Glide.with(requireContext()).load(AppConstants.ImageBaseURL + movie.getPoster_path())
                        .centerCrop()
                        .into(binding.moviePoster);

                binding.movieName.setText(movie.getTitle());
                hour = movie.getRuntime() / 60;
                min = movie.getRuntime() % 60;
                binding.movieRuntime.setText(hour + "h " + min + "m");
                binding.moviePlot.setText(movie.getOverview());

                genre = "";
                for (int i = 0; i<movie.getGenres().size(); i++){
                    if (i == movie.getGenres().size()-1)
                        genre += movie.getGenres().get(i).getName();
                    else
                        genre += movie.getGenres().get(i).getName() + " | ";

                }

                binding.movieGenre.setText(genre);
                binding.playTrailer.setVisibility(View.VISIBLE);
                binding.movieCastText.setVisibility(View.VISIBLE);
                binding.moviePlotText.setVisibility(View.VISIBLE);

                JsonArray array = movie.getVideos().getAsJsonArray("results");
                videoId = array.get(0).getAsJsonObject().get("key").getAsString();


            }
        });

        viewModel.getMovieCastList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cast>>() {
            @Override
            public void onChanged(ArrayList<Cast> casts) {
                castAdapter.setCastList(casts);
                castAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
