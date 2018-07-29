package com.arctouch.codechallenge.home.dagger;

import android.content.Context;

import com.arctouch.codechallenge.BuildConfig;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@Module(includes = ContextModule.class)
public class NetworkModule {

    private final int MAX_SIZE = 10 * 1000 * 1024;
    private final String CACHE_FILE_NAME = "okhttp_cache";

    @Provides
    @HomeScope
    public OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .cache(cache);
        if (BuildConfig.DEBUG) clientBuilder.addInterceptor(httpLoggingInterceptor);

        return clientBuilder.build();
    }

    @Provides
    @HomeScope
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, MAX_SIZE);
    }

    @Provides
    @HomeScope
    public File cacheFile(Context context) {
        return new File(context.getCacheDir(), CACHE_FILE_NAME);
    }

    @Provides
    @HomeScope
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }
}
