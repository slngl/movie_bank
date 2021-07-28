package com.slngl.moviebank.network.interceptors;

import com.slngl.moviebank.base.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class QueryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstants.tmdbApiKey)
                .build();

        Request newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build();

        return chain.proceed(newRequest);
    }
}
