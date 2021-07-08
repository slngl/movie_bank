package com.slngl.moviebank.di.modules;

import com.slngl.moviebank.di.qualifiers.BaseUrlQualifier;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {
    @Provides
    @Singleton
    @BaseUrlQualifier
    public String provideBaseUrl() {
        return "https://api.themoviedb.org/3/";
    }
}
