package com.thanasis.movieapp;

public class Movie {
    private int movieId;
    private String movieName;
    private String movieDirector;
    private String lengthMinutes;
    private String movieType;
    private String movieYear;

    public Movie(int movieId, String movieName, String movieDirector, String lengthMinutes, String movieType, String movieYear) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.lengthMinutes = lengthMinutes;
        this.movieType = movieType;
        this.movieYear = movieYear;
    }

    public Movie(String movieName, String movieDirector, String lengthMinutes, String movieType, String movieYear) {
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.lengthMinutes = lengthMinutes;
        this.movieType = movieType;
        this.movieYear = movieYear;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(String lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }
}
