package com.slngl.moviebank.network;

import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.model.MovieResponse;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface MovieApiService {
    //dcfd36b48a497a19d36968a699d24add

    @GET("movie/now_playing")
    Observable<MovieResponse> getCurrentlyShowing();

    @GET("movie/popular")
    Observable<MovieResponse> getPopular();

    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcoming();

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRated();

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") int id);

}
