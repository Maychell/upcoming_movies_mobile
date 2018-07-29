package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.model.Movie;

import java.util.List;

public interface HomeCallback {
    void feedMovies(List<Movie> movies);
    void setProgressVisibile(boolean visibility);
}
