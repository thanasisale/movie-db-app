package com.thanasis.movieapp;

import android.provider.BaseColumns;

public class MovieDBCrontract {

    private MovieDBCrontract(){}

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_NAME_MOVIE_DIR = "movie_director";
        public static final String COLUMN_NAME_MOVIE_LENGTH = "movie_length";
        public static final String COLUMN_NAME_MOVIE_TYPE = "movie_type";
        public static final String COLUMN_NAME_MOVIE_YEAR = "movie_year";
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";

    }
}
