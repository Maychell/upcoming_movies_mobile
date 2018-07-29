package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private HomeCallback mView;
    private TmdbApi mApi;
    private long currentPage = 1;
    private int totalPages = Integer.MAX_VALUE;

    public HomePresenter(HomeCallback homeCallback, TmdbApi api) {
        this.mView = homeCallback;
        this.mApi = api;
    }

    public void loadMovies() {
        if (currentPage > totalPages) {
            return;
        }
        fetchMovies();
        ++currentPage;
    }

    private void fetchMovies() {
        mView.setProgressVisibile(true);
        mApi.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, currentPage, TmdbApi.DEFAULT_REGION)
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
                    totalPages = response.totalPages;
                    mView.setProgressVisibile(false);
                    mView.feedMovies(response.results);
                });
    }
}
