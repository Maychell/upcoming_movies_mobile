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
    public void setReturnedMoviesToAdapterOnLoadMovies() {
        UpcomingMoviesResponse response = new UpcomingMoviesResponse();
        List<Movie> mMovies = new ArrayList<>();
        response.results = mMovies;
        Mockito.when(api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION))
                .thenReturn(Observable.just(response));
        mPresenter.loadMovies();
        Mockito.verify(mView, Mockito.times(1)).setAdapter();
    }
}
