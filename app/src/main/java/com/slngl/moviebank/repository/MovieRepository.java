package com.slngl.moviebank.repository;

import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.model.MovieResponse;
import com.slngl.moviebank.network.MovieApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieRepository {

    MovieApiService apiService;

    @Inject
    public MovieRepository(MovieApiService apiService){
        this.apiService=apiService;
    }

    public Observable<MovieResponse> getCurrentlyShowing(){
        return apiService.getCurrentlyShowing();
    }

    public Observable<MovieResponse> getPopular(){
        return apiService.getPopular();
    }

    public Observable<MovieResponse> getTopRated(){
        return apiService.getTopRated();
    }

    public Observable<MovieResponse> getUpcomig(){
        return apiService.getUpcoming();
    }

    public Observable<Movie> getMovieDetails(int movieId){
        return apiService.getMovieDetails(movieId);
    }
}
