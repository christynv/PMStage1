package com.example.pmstage1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils  {

    public static List<Movie> parseMovieJson(String json) {

        List<Movie> movieList = new ArrayList<>();
        JSONObject mainObject;
        String title;
        float vote_average;
        String release_date;
        String poster_path;
        String overview;
        int id;

        try {
            mainObject = new JSONObject(json);
            JSONArray resultsArray = mainObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieObject = resultsArray.getJSONObject(i);
                title = movieObject.getString("title");
                vote_average = movieObject.getLong("vote_average");
                release_date = movieObject.getString("release_date");
                poster_path = movieObject.getString("poster_path");
                overview = movieObject.getString("overview");
                id = movieObject.getInt("id");

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setVoteAverage(vote_average);
                movie.setReleaseDate(release_date);
                movie.setPoster(poster_path);
                movie.setOverview(overview);
                movie.setId(id);

                movieList.add(i, movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }
}
