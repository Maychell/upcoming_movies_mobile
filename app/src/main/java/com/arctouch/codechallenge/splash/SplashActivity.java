package com.arctouch.codechallenge.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.splash.dagger.ContextModule;
import com.arctouch.codechallenge.splash.dagger.DaggerSplashComponent;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity {

    @Inject SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        DaggerSplashComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
        mPresenter.loadGenres();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
