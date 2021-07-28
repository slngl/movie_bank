package com.slngl.moviebank.usecase;

import com.google.gson.JsonObject;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.model.MovieResponse;
import com.slngl.moviebank.repository.MovieRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class MovieUsecase {

    MovieRepository movieRepository;

    @Inject
    public MovieUsecase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Observable<MovieResponse> getPopular() {
        return movieRepository.getPopular();

    }

    public Observable<MovieResponse> getTopRated() {
        return movieRepository.getTopRated();
    }

    public Observable<MovieResponse> getUpcomig() {
        return movieRepository.getUpcomig();
    }

    public Observable<JsonObject> getCast(Integer movieId) {
        return movieRepository.getCast(movieId);
    }

    public Observable<Movie> getMovieDetails(Integer movieId) {
        return movieRepository.getMovieDetails(movieId);
    }


}
