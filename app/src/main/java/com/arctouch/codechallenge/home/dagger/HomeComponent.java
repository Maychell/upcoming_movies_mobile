package com.arctouch.codechallenge.home.dagger;

import com.arctouch.codechallenge.home.HomeActivity;

import dagger.Component;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@HomeScope
@Component(modules = { HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
