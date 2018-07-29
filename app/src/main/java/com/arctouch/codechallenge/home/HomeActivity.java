package com.arctouch.codechallenge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.base.BaseActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.movie_details.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeCallback {

    public static final String MOVIE_EXTRA_TAG = "MovieTag";
    private static final String MOVIE_LIST_STATE = "movieListState";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);
        mPresenter = new HomePresenter(this, api);

        if (savedInstanceState != null) {
            List<Movie> mMovies = (List<Movie>) savedInstanceState.getSerializable(MOVIE_LIST_STATE);
            mPresenter.setMovies(mMovies);
        } else {
            mPresenter.loadMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIE_LIST_STATE, mPresenter.getMovies());
    }

    @Override
    public void setAdapter() {
        recyclerView.setAdapter(new HomeAdapter(mPresenter.getMovies(), movie -> {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(MOVIE_EXTRA_TAG, movie);
            startActivity(intent);
        }));
        progressBar.setVisibility(View.GONE);
    }
}
