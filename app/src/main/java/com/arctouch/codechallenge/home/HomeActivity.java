package com.arctouch.codechallenge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.base.BaseActivity;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.movie_details.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends BaseActivity {

    public static final String MOVIE_EXTRA_TAG = "MovieTag";
    private static final String MOVIE_LIST_STATE = "movieListState";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Movie> mMovies;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);

        if (savedInstanceState != null) {
            mMovies = (List<Movie>) savedInstanceState.getSerializable(MOVIE_LIST_STATE);
            setAdapter();
        } else {
            loadMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIE_LIST_STATE, new ArrayList<> (mMovies));
    }

    private void setAdapter() {
        recyclerView.setAdapter(new HomeAdapter(mMovies, movie -> {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(MOVIE_EXTRA_TAG, movie);
            startActivity(intent);
        }));
        progressBar.setVisibility(View.GONE);
    }

    private void loadMovies() {
        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    mMovies = response.results;
                    setAdapter();
                });
    }
}
