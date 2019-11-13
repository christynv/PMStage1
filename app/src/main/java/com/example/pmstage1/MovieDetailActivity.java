package com.example.pmstage1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_POSTER = "extra_movie_poster";
    public static final String MOVIE_TITLE = "extra_movie_title";
    public static final String MOVIE_RELEASE_DATE = "extra_movie_release_date";
    public static final String MOVIE_RATING = "extra_movie_rating";
    public static final String MOVIE_OVERVIEW = "extra_movie_overview";
    private static final String BASE_URL = "https://image.tmdb.org/t/p/w500";

    private ImageView posterIv;
    private TextView titleTv;
    private TextView releaseDateTv;
    private RatingBar ratingTv;
    private TextView overviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        posterIv = findViewById(R.id.iv_movie_poster);
        releaseDateTv = findViewById(R.id.tv_movie_release);
        titleTv = findViewById(R.id.tv_movie_title);
        ratingTv = findViewById(R.id.tv_movie_rating);
        overviewTv = findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }

        Bundle movie = intent.getExtras();
        if( movie != null ) {
            titleTv.setText(movie.getString(MOVIE_TITLE, "title"));
            Picasso.with(posterIv.getContext())
                    .load(BASE_URL + movie.getString(MOVIE_POSTER, "poster"))
                    .into(posterIv);
            releaseDateTv.setText(movie.getString(MOVIE_RELEASE_DATE, "release_date"));
            ratingTv.setRating(movie.getFloat(MOVIE_RATING, 0f)/2);
            overviewTv.setText(movie.getString(MOVIE_OVERVIEW, "overview"));
        } else finish();
    }
}
