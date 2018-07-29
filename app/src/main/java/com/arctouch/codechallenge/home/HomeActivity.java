package com.arctouch.codechallenge.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.dagger.ContextModule;
import com.arctouch.codechallenge.home.dagger.DaggerHomeComponent;
import com.arctouch.codechallenge.home.dagger.HomeModule;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeCallback {

    public static final String MOVIE_EXTRA_TAG = "MovieTag";
    private static final String MOVIE_LIST_STATE = "movieListState";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    @Inject HomePresenter mPresenter;
    @Inject HomeAdapter mAdapter;
    @Inject LinearLayoutManager mLayoutManager;
    private Boolean isScrolling = false;
    private List<Movie> mMovies = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        DaggerHomeComponent.builder()
                .contextModule(new ContextModule(this))
                .homeModule(new HomeModule(this, mMovies))
                .build()
                .inject(this);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
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
}
