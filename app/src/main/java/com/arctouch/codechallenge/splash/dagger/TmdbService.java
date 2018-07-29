package com.arctouch.codechallenge.splash.dagger;

import com.arctouch.codechallenge.api.TmdbApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@Module(includes = NetworkModule.class)
public class TmdbService {

    @Provides
    @SplashScope
    TmdbApi getTmdbApi(Retrofit retrofit) {
        return retrofit.create(TmdbApi.class);
    }

    @Provides
    @SplashScope
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}