package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.helper.RxSchedulersOverrideRule;
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
    }

    @Test
    public void whenMoviesAdded() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        Mockito.when(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION))
                .thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(1)).setProgressVisibile(true);
        Mockito.verify(mView, Mockito.times(1)).setProgressVisibile(false);
        Mockito.verify(mView, Mockito.times(1)).feedMovies(mMovies);
    }

    @Test
    public void whenThereIsOnlyOneMoviesPage() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        response.totalPages = 1;
        Mockito.when(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION))
                .thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(1)).setProgressVisibile(true);
        Mockito.verify(mView, Mockito.times(1)).setProgressVisibile(false);
        Mockito.verify(mView, Mockito.times(1)).feedMovies(mMovies);
    }

    @Test
    public void whenThereAreMoreThanOneMoviesPages() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        response.totalPages = 3;
        Mockito.when(api.upcomingMovies(Matchers.eq(TmdbApi.API_KEY), Matchers.eq(TmdbApi.DEFAULT_LANGUAGE), Matchers.anyLong(), Matchers.eq(TmdbApi.DEFAULT_REGION)))
                .thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(3)).setProgressVisibile(true);
        Mockito.verify(mView, Mockito.times(3)).setProgressVisibile(false);
        Mockito.verify(mView, Mockito.times(3)).feedMovies(mMovies);
    }
}
