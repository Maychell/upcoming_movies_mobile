package com.arctouch.codechallenge.splash.dagger;

import com.arctouch.codechallenge.splash.SplashActivity;

import dagger.Component;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@SplashScope
@Component(modules = { SplashModule.class})
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
