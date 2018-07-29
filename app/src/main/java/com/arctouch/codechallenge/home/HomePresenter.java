package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private HomeCallback mView;
    private TmdbApi mApi;
    private List<Movie> mMovies;

    public HomePresenter(HomeCallback homeCallback, TmdbApi api) {
        this.mView = homeCallback;
        this.mApi = api;
    }

    public void loadMovies() {
        mApi.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION)
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
                    setMovies(response.results);
                });
    }

    public ArrayList<Movie> getMovies() {
        return (ArrayList<Movie>) mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        mView.setAdapter();
    }
}
