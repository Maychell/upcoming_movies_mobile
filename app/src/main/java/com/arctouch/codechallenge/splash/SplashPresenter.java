package com.arctouch.codechallenge.splash;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

public class SplashPresenter {

    private TmdbApi mApi;

    public SplashPresenter(TmdbApi api) {
        mApi = api;
    }

    public void loadGenres() {
        mApi.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);
                });
    }
}
