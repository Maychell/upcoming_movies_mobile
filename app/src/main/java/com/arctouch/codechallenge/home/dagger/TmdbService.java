package com.arctouch.codechallenge.home.dagger;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.app.Constants;

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
    @HomeScope
    TmdbApi getTmdbApi(Retrofit retrofit) {
        return retrofit.create(TmdbApi.class);
    }

    @Provides
    @HomeScope
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}