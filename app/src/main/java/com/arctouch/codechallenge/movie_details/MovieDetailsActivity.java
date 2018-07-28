package com.arctouch.codechallenge.movie_details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by MaychellFernandes on 27/07/18.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView genresTextView;
    private TextView releaseDateTextView;
    private TextView overviewTextView;
    private ImageView posterImageView;
    private ImageView backdropImageView;

    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        this.titleTextView = findViewById(R.id.titleTextView);
        this.genresTextView = findViewById(R.id.genresTextView);
        this.releaseDateTextView = findViewById(R.id.releaseDateTextView);
        this.overviewTextView = findViewById(R.id.overviewTextView);
        this.posterImageView = findViewById(R.id.posterImageView);
        this.backdropImageView = findViewById(R.id.backdropImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        setMovieData(intent.getParcelableExtra(HomeActivity.MOVIE_EXTRA_TAG));
    }

    private void setMovieData(Movie movie) {
        titleTextView.setText(movie.title);
        genresTextView.setText(TextUtils.join(", ", movie.genres));
        releaseDateTextView.setText(String.format(getString(R.string.released_on), movie.releaseDate));
        overviewTextView.setText(movie.overview);
        setPosterImage(movie.posterPath);
        setBackdropImage(movie.backdropPath);
    }

    private void setPosterImage(String posterImagePath) {
        if (TextUtils.isEmpty(posterImagePath)) {
            return;
        }

        Glide.with(this)
                .load(movieImageUrlBuilder.buildPosterUrl(posterImagePath))
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(posterImageView);
    }

    private void setBackdropImage(String backdropImagePath) {
        if (TextUtils.isEmpty(backdropImagePath)) {
            return;
        }

        Glide.with(this)
                .load(movieImageUrlBuilder.buildBackdropUrl(backdropImagePath))
                .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(backdropImageView);
    }
}
