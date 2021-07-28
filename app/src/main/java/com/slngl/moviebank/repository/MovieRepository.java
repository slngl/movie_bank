package com.slngl.moviebank.repository;

import com.google.gson.JsonObject;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.model.MovieResponse;
import com.slngl.moviebank.network.MovieApiService;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MovieRepository {

    MovieApiService apiService;

    @Inject
    public MovieRepository(MovieApiService apiService){
        this.apiService=apiService;
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

    public Observable<JsonObject>  getCast(int movieId){
        return apiService.getCast(movieId);
    }
}
