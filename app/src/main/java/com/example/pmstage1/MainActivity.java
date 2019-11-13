package com.example.pmstage1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.ListItemClickListener {

    private String TAG = MainActivity.class.getSimpleName();

    private static final String NOW_URL = "https://api.themoviedb.org/3/movie/now_playing?";
    private static final String POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?";
    private static final String TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?";
    private static final String API_KEY = "yourAPIhere";
    private static final String PAGE_NUM = "&page=";
    private static String URL = NOW_URL;

    private static List<Movie> movieList;
    private static MovieAdapter movieAdapter;
    private RecyclerView rvMovieList;
    private GridLayoutManager layoutManager;
    private int pageNum = 1;
    private boolean retrieveMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovieList = findViewById(R.id.rv_all_movies);
        layoutManager = new GridLayoutManager(this, 3);
        rvMovieList.setLayoutManager(layoutManager);

        onScrollListener();
        new getMovies().execute();
    }

    private class getMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            retrieveMovies = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();

                String json = NetworkUtils.getMovieURL(URL + API_KEY + PAGE_NUM + pageNum);

                if (json != null) {
                    movieList = JsonUtils.parseMovieJson(json);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            retrieveMovies = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (movieAdapter == null) {
                movieAdapter = new MovieAdapter(movieList, MainActivity.this);
                rvMovieList.setAdapter(movieAdapter);
            } else {
                movieAdapter.addMovies(movieList);
                rvMovieList.setAdapter(movieAdapter);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sortClicked = item.getItemId();

        switch(sortClicked) {
            case R.id.now_playing:
                URL = NOW_URL;
                movieAdapter = null;
                pageNum = 1;
                new getMovies().execute();
                setTitle("Movies");
                break;
            case R.id.pop_movies:
                URL = POPULAR_URL;
                movieAdapter = null;
                pageNum = 1;
                new getMovies().execute();
                setTitle("Popular Movies");
                break;
            case R.id.tr_movies:
                URL = TOP_RATED_URL;
                movieAdapter = null;
                pageNum = 1;
                new getMovies().execute();
                setTitle("Top Rated Movies");
                break;
            default:
                URL = NOW_URL;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(Movie movie) {
        Log.e(TAG, "Movie " + movie.getTitle());
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_POSTER, movie.getPoster());
        intent.putExtra(MovieDetailActivity.MOVIE_TITLE, movie.getTitle());
        intent.putExtra(MovieDetailActivity.MOVIE_RELEASE_DATE, "Released: " + movie.getReleaseDate());
        intent.putExtra(MovieDetailActivity.MOVIE_RATING, movie.getVoteAverage());
        intent.putExtra(MovieDetailActivity.MOVIE_OVERVIEW, "Description: " + movie.getOverview());
        startActivity(intent);
    }

    private void onScrollListener() {
        rvMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if(firstVisibleItem + visibleItemCount >= totalItemCount/2) {
                    if (!retrieveMovies) {
                        pageNum++;
                        new getMovies().execute();
                    }
                }
            }
        });
    }
}
