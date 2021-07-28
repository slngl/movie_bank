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
    private final MovieRepository repository;

    private final MutableLiveData<ArrayList<Movie>> popularMovieList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> topRatedMovieList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> upcomingMovieList = new MutableLiveData<>();

    @Inject
    public HomeViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Movie>> getPopularMoviesList() {
        return popularMovieList;
    }

    public MutableLiveData<ArrayList<Movie>> getTopRatedMoviesList() {
        return topRatedMovieList;
    }

    public MutableLiveData<ArrayList<Movie>> getUpcomingMoviesList() {
        return upcomingMovieList;
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



    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
