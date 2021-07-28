package com.slngl.moviebank.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.usecase.MovieUsecase;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MovieUsecase usecase;

    private final MutableLiveData<ArrayList<Movie>> popularMovieList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> topRatedMovieList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Movie>> upcomingMovieList = new MutableLiveData<>();

    @Inject
    public HomeViewModel(MovieUsecase usecase) {
        this.usecase = usecase;
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
        disposable.add(usecase.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> popularMovieList.setValue(result.getResults()),
                        error -> Log.e("HomeViewModel", "getPopularMovies: " + error.getMessage())));

    }

    public void getTopRatedMovies() {
        disposable.add(usecase.getTopRated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> topRatedMovieList.setValue(result.getResults()),
                        error -> Log.e("HomeViewModel", "getTopRated: " + error.getMessage())));
    }

    public void getUpComingMovies() {
        disposable.add(usecase.getUpcomig()
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
