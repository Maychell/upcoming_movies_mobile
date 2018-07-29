package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.helper.RxSchedulersOverrideRule;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.UpcomingMoviesResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by maychellfernandesdeoliveira on 28/07/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    @InjectMocks private HomePresenter mPresenter;
    @Mock private TmdbApi api;
    @Mock private HomeCallback mView;
    @Rule public RxSchedulersOverrideRule schedulersOverrideRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mPresenter = new HomePresenter(mView, api);
        Mockito.when(api.getGenres()).thenReturn(Observable.never());
        Mockito.when(api.getUpcomingMovies(Matchers.anyLong())).thenReturn(Observable.never());
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre());
        Cache.setGenres(genres);
    }

    @Test
    public void whenThereIsOnlyOneMoviesPage() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        response.totalPages = 1;
        Mockito.when(api.getUpcomingMovies(1L)).thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(1)).feedMovies(mMovies);
    }

    @Test
    public void whenThereAreMoreThanOneMoviesPages() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        response.totalPages = 3;
        Mockito.when(api.getUpcomingMovies(Matchers.anyLong())).thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(3)).feedMovies(mMovies);
    }

    @Test
    public void whenCachedGenresIsEmpty() {
        Cache.setGenres(new ArrayList<>());
        mPresenter.loadMovies();
        Mockito.verify(api, Mockito.times(1)).getGenres();
    }

    @Test
    public void whenCachedGenresIsNotEmpty() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre());
        Cache.setGenres(genres);
        mPresenter.loadMovies();
        Mockito.verify(api, Mockito.never()).getGenres();
    }
}
