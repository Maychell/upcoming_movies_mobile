package com.arctouch.codechallenge.splash.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@Module
public class ContextModule {
    private final Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @SplashScope
    public Context context() {
        return mContext;
    }
}
