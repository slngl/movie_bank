package com.slngl.moviebank.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.slngl.moviebank.databinding.FragmentHomeBinding;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.view.adapters.HomeMoviesAdapter;
import com.slngl.moviebank.viewModel.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private HomeMoviesAdapter upcomingAdapter, popularAdapter, topRatedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(HomeFragment.this).get(HomeViewModel.class);


        viewModel.getPopularMovies();
        viewModel.getTopRatedMovies();
        viewModel.getUpComingMovies();

        observeData();

    }

    private void observeData() {


        viewModel.getPopularMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                popularAdapter = new HomeMoviesAdapter(movies, getContext());
                popularAdapter.notifyDataSetChanged();
                binding.popularRecyclerView.setAdapter(popularAdapter);
            }
        });

        viewModel.getTopRatedMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                topRatedAdapter = new HomeMoviesAdapter(movies, getContext());
                topRatedAdapter.notifyDataSetChanged();
                binding.topRatedRecyclerView.setAdapter(topRatedAdapter);
            }
        });

        viewModel.getUpcomingMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                upcomingAdapter = new HomeMoviesAdapter(movies, getContext());
                upcomingAdapter.notifyDataSetChanged();
                binding.upcomingRecyclerView.setAdapter(upcomingAdapter);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
