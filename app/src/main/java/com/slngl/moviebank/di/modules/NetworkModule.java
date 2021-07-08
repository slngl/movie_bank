package com.slngl.moviebank.di.modules;

import com.slngl.moviebank.di.qualifiers.BaseUrlQualifier;
import com.slngl.moviebank.network.MovieApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public static MovieApiService provideMovieApiService(@BaseUrlQualifier String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MovieApiService.class);
    }
}
