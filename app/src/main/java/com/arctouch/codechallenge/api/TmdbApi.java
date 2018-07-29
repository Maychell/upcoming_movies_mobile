package com.arctouch.codechallenge.api;

import com.arctouch.codechallenge.app.Constants;
import com.arctouch.codechallenge.model.GenreResponse;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.UpcomingMoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {
    @GET(Constants.MovieService.GENRE_LIST)
    Observable<GenreResponse> getGenres();

    @GET(Constants.MovieService.UPCOMING_MOVIES)
    Observable<UpcomingMoviesResponse> getUpcomingMovies(@Query("page") Long page);

    @GET(Constants.MovieService.GET_MOVIE)
    Observable<Movie> getMovies(@Path("id") Long id);
}
