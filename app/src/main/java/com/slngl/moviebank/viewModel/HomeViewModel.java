package com.slngl.moviebank.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.repository.MovieRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
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

    public void getPopularMovies() {
        disposable.add(repository.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> popularMovieList.setValue(result.getResults()),
                        error -> Log.e("HomeViewModel", "getPopularMovies: " + error.getMessage())));

    }
}
