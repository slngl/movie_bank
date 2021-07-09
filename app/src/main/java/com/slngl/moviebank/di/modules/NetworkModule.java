package com.slngl.moviebank.di.modules;

import com.slngl.moviebank.di.qualifiers.BaseUrlQualifier;
import com.slngl.moviebank.network.MovieApiService;
import com.slngl.moviebank.network.interceptors.QueryInterceptor;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public static MovieApiService provideMovieApiService(@BaseUrlQualifier String baseUrl, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MovieApiService.class);
    }

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        return builder.build();
    }

    @Singleton
    @Provides
    public static OkHttpClient.Builder provideOkHttpBuilder(
            QueryInterceptor queryInterceptor,
            HttpLoggingInterceptor loggingInterceptor
    ){
        return new OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(queryInterceptor);

    }

    @Provides
    @Singleton
    public static QueryInterceptor provideQueryInterceptor(){
        return new QueryInterceptor();
    }

    @Singleton
    @Provides
    public static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
    
}
