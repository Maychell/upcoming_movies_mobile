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
    private long currentPage = 0;
    private int totalPages = Integer.MAX_VALUE;

    public HomePresenter(HomeCallback homeCallback, TmdbApi api) {
        this.mView = homeCallback;
        this.mApi = api;
    }

    public void loadMovies() {
        if (currentPage >= totalPages) {
            return;
        }
        loadGenres();
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private void loadGenres() {
        if (!Cache.getGenres().isEmpty()) {
            fetchMovies();
            return;
        }
        mView.setProgressVisibile(true);
        mApi.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);
                    mView.setProgressVisibile(false);
                    fetchMovies();
                });
    }

    private void fetchMovies() {
        mView.setProgressVisibile(true);
        mApi.getUpcomingMovies(++currentPage)
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
