package com.arctouch.codechallenge.home.dagger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.home.HomeAdapter;
import com.arctouch.codechallenge.home.HomePresenter;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.movie_details.MovieDetailsActivity;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaychellFernandes on 29/07/18.
 */

@Module(includes = TmdbService.class)
public class HomeModule {
    private final HomeActivity mHomeActivity;
    private final List<Movie> mMovies;

    public HomeModule(HomeActivity homeActivity, List<Movie> movies) {
        this.mHomeActivity = homeActivity;
        this.mMovies = movies;
    }

    @Provides
    @HomeScope
    public HomePresenter presenter(TmdbApi tmdbApi) {
        return new HomePresenter(mHomeActivity, tmdbApi);
    }

    @Provides
    @HomeScope
    public HomeAdapter adapter(Context context) {
        return new HomeAdapter(mMovies, movie -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(HomeActivity.MOVIE_EXTRA_TAG, movie);
            context.startActivity(intent);
        });
    }

    @Provides
    @HomeScope
    public LinearLayoutManager linearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
