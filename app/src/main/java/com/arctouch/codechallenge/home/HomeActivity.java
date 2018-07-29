package com.arctouch.codechallenge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
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
    private HomeAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Boolean isScrolling = false;
    private List<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        mPresenter = new HomePresenter(this, api);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new HomeAdapter(mMovies, this::onMovieClick);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        setScrollListener();

        if (savedInstanceState != null) {
            feedMovies((List<Movie>) savedInstanceState.getSerializable(MOVIE_LIST_STATE));
        } else {
            mPresenter.loadMovies();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable(MOVIE_LIST_STATE, new ArrayList<> (mMovies));
    }

    private void setScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItemsCount = mLayoutManager.getChildCount();
                int totalItems = mLayoutManager.getItemCount();
                int scrollOutItems = mLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItemsCount + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    mPresenter.loadMovies();
                }
            }
        });
    }

    @Override
    public void feedMovies(List<Movie> movies) {
        mMovies.addAll(movies);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setProgressVisibile(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA_TAG, movie);
        startActivity(intent);
    }
}
