package com.slngl.moviebank.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.slngl.moviebank.model.Cast;
import com.slngl.moviebank.model.Genre;
import com.slngl.moviebank.model.Movie;
import com.slngl.moviebank.usecase.MovieUsecase;

import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MovieDetailsViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MovieUsecase usecase;

    private final MutableLiveData<ArrayList<Cast>> movieCastList = new MutableLiveData<>();

    private final MutableLiveData<Movie> movieDetails = new MutableLiveData<>();

    public MutableLiveData<Movie> getMovieDetails() {
        return movieDetails;
    }


    @Inject
    public MovieDetailsViewModel(MovieUsecase usecase) {
        this.usecase = usecase;
    }

    public void getMovieDetails(int movieId) {
        disposable.add(usecase.getMovieDetails(movieId)
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
                .subscribe(movieDetails::setValue,
                        error -> Log.e("HomeViewModel", "getMovieDetails:" + error.getMessage()))
        );
    }

    public void getCasts(int movieId) {
        disposable.add(usecase.getCast(movieId)
                .subscribeOn(Schedulers.io())
                .map(new Function<JsonObject, ArrayList<Cast>>() {
                    @Override
                    public ArrayList<Cast> apply(JsonObject jsonObject) throws Throwable {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("cast");
                        return new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Cast>>() {
                        }.getType());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieCastList::setValue,
                        error -> Log.e("MovieDetailsViewModel", "getCastList" + error.getMessage()))
        );
    }

    public MutableLiveData<ArrayList<Cast>> getMovieCastList() {
        return movieCastList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
