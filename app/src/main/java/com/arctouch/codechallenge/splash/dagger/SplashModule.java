package com.arctouch.codechallenge.splash.dagger;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.splash.SplashPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@Module(includes = TmdbService.class)
public class SplashModule {
    @Provides
    @SplashScope
    public SplashPresenter presenter(TmdbApi tmdbApi) {
        return new SplashPresenter(tmdbApi);
    }
}
