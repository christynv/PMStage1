package com.example.pmstage1;

public class Movie {

    private String title;
    private float vote_average;
    private String release_date;
    private String poster_path;
    private String overview;
    private int id;


    public Movie() {
    }

    public Movie(String title, float vote_average, String release_date, String poster_path, String overview, int id) {
        this.title = title;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.overview = overview;
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public float getVoteAverage() { return vote_average; }

    public void setVoteAverage(float vote_average) { this.vote_average = vote_average; }

    public String getReleaseDate() { return release_date; }

    public void setReleaseDate(String release_date) { this.release_date = release_date; }

    public String getPoster() { return poster_path; }

    public void setPoster(String poster_path) { this.poster_path = poster_path; }

    public String getOverview() { return overview; }

    public void setOverview(String overview) { this.overview = overview; }

    public void setId(int id) { this.id = id; }
}
