package com.arctouch.codechallenge.app;

import com.arctouch.codechallenge.BuildConfig;

/**
 * Created by maychellfernandesdeoliveira on 29/07/2018.
 */

public class Constants {

    public static final String DEFAULT_LANGUAGE = "pt-BR";
    public static final String DEFAULT_REGION = "BR";
    public static final String URL = BuildConfig.TmdbApiUrl;
    public static final String API_KEY = BuildConfig.TmdbApiKey;

    public class MovieService {
        public static final String GENRE_LIST = "genre/movie/list";
        public static final String UPCOMING_MOVIES = "movie/upcoming";
        public static final String GET_MOVIE = "movie/{id}";
    }
}
