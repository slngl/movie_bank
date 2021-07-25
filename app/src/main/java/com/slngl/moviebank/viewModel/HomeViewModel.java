package com.slngl.moviebank.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slngl.moviebank.model.Genre;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.model.MovieResponse;
import com.slngl.moviebank.repository.MovieRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private MovieRepository repository;

    private MutableLiveData<ArrayList<Movie>> currentMovieList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> popularMovieList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> topRatedMovieList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> upcomingMovieList = new MutableLiveData<>();
    private MutableLiveData<Movie> movieDetails = new MutableLiveData<>();

    @Inject
    public HomeViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Movie>> getPopularMoviesList() {
        return popularMovieList;
    }

    public MutableLiveData<ArrayList<Movie>> getCurrentlyShowingList() {
        return currentMovieList;
    }

    public MutableLiveData<ArrayList<Movie>> getTopRatedMoviesList() {
        return topRatedMovieList;
    }

    public MutableLiveData<ArrayList<Movie>> getUpcomingMoviesList() {
        return upcomingMovieList;
    }

    public MutableLiveData<Movie> getMovieDetails() {
        return movieDetails;
    }

    public void getCurrentlyShowingMovies() {
        disposable.add(repository.getCurrentlyShowing()
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieResponse movieResponse) throws Throwable {
                        return movieResponse.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Movie> movies) {
                        currentMovieList.setValue(movies);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void getPopularMovies() {
        disposable.add(repository.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> popularMovieList.setValue(result.getResults()),
                        error -> Log.e("HomeViewModel", "getPopularMovies: " + error.getMessage())));

    }

    public void getTopRatedMovies() {
        disposable.add(repository.getTopRated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> topRatedMovieList.setValue(result.getResults()),
                        error -> Log.e("HomeViewModel", "getTopRated: " + error.getMessage())));
    }

    public void getUpComingMovies() {
        disposable.add(repository.getUpcomig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> upcomingMovieList.setValue(movieResponse.getResults()),
                        error -> Log.e("HomeViewModel", "getUpComing: " + error.getMessage()))
        );
    }

    public void getMovieDetails(int movieId) {
        disposable.add(repository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .map(new Function<Movie, Movie>() {
                    @Override
                    public Movie apply(Movie movie) throws Throwable {
                        ArrayList<String> genreNames = new ArrayList<>();
                        //MovieResponse gives list of genre(object) so we will map each id toit genre name here

                        for (Genre genre : movie.getGenres()) {
                            genreNames.add(genre.getName());
                        }
                        movie.setGenre_names(genreNames);
                        return movie;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> movieDetails.setValue(result),
                        error -> Log.e("HomeViewModel", "getMovieDetails:" + error.getMessage()))
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
