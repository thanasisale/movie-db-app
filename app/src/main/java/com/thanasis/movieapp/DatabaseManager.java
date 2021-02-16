package com.thanasis.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MovieAppDB.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + MovieDBCrontract.MovieEntry.TABLE_NAME + " (" +
                    MovieDBCrontract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR + TEXT_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH + TEXT_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE + TEXT_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR + TEXT_TYPE + " )";
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS "+ MovieDBCrontract.UserEntry.TABLE_NAME +" (" +
                    MovieDBCrontract.UserEntry._ID + " INTEGER PRIMARY KEY, " +
                    MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + ")";

    private static final String SQL_DELETE_MOVIE_ENTRIES =
                "DROP TABLE IF EXISTS "+ MovieDBCrontract.MovieEntry.TABLE_NAME;
    private static final String SQL_DELETE_USER_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieDBCrontract.UserEntry.TABLE_NAME;

    // Sample data to add
    private static final String SQL_ADD_ADMIN_USER =
            "INSERT INTO " + MovieDBCrontract.UserEntry.TABLE_NAME + "(" +
                    MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME + COMMA_SEP +
                    MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD + ") VALUES ('admin','1234')";
    private static final String SQL_ADD_MOVIE_1 =
            "INSERT INTO " + MovieDBCrontract.MovieEntry.TABLE_NAME + "(" +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR +
                    ") VALUES ('1917','Sam Mendes','120', 'War', '2019')";
    private static final String SQL_ADD_MOVIE_2 =
            "INSERT INTO " + MovieDBCrontract.MovieEntry.TABLE_NAME + "(" +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE + COMMA_SEP +
                    MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR +
                    ") VALUES ('Interstellar','Christopher Nolan','180', 'Sci-Fi', '2014')";

    SQLiteDatabase database;

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        // Add the test entries
        db.execSQL(SQL_ADD_ADMIN_USER);
        db.execSQL(SQL_ADD_MOVIE_1);
        db.execSQL(SQL_ADD_MOVIE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MOVIE_ENTRIES);
        db.execSQL(SQL_DELETE_USER_ENTRIES);
        onCreate(db);
    }

    public Boolean usernameFreeExistingUser(int id, String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {MovieDBCrontract.UserEntry._ID, MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME};
        String selection = MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(MovieDBCrontract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            if(id != cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.UserEntry._ID))){
                cursor.close();
                return true;
            }else {
                cursor.close();
                return false;
            }
        }
        else {
            cursor.close();
            return false;
        }
    }

    public Boolean usernameFree(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME};
        String selection = MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(MovieDBCrontract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if(cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public int checkUserExists(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {MovieDBCrontract.UserEntry._ID, MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME, MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD};
        String selection = MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME + "=? and " + MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("user", projection, selection, selectionArgs, null, null,null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            int userID = cursor.getInt((cursor.getColumnIndex(MovieDBCrontract.UserEntry._ID)));
            cursor.close();
            return userID;
        }
        else {
            cursor.close();
            return -1;
        }
    }

    public void addNewUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD, password);

        db.insert(MovieDBCrontract.UserEntry.TABLE_NAME, null, values);
    }

    public String[] getCurrentUserCredentials(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME, MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD};
        String selection = MovieDBCrontract.UserEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor c = db.query(MovieDBCrontract.UserEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            String [] userCredentials = {
                    c.getString(c.getColumnIndex(MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME)),
                    c.getString(c.getColumnIndex(MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD))
            };
            c.close();
            return userCredentials;
        } else {
            c.close();
            return null;
        }
    }

    public int updateCredentials(int userId, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieDBCrontract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(MovieDBCrontract.UserEntry.COLUMN_NAME_PASSWORD, password);

        String selection = MovieDBCrontract.UserEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        return db.update(MovieDBCrontract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    public boolean addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE, movie.getMovieName());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR, movie.getMovieDirector());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH, movie.getLengthMinutes());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE, movie.getMovieType());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR, movie.getMovieYear());

        long result = db.insert(MovieDBCrontract.MovieEntry.TABLE_NAME, null, values);

        return result != -1;
    }

    public ArrayList<Movie> getAllMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();
        String[] projection = {
                MovieDBCrontract.MovieEntry._ID,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR
        };

        Cursor cursor = db.query(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR))
            );
            movieList.add(movie);
        }
        cursor.close();
        return movieList;
    }

    public Movie getSingleMovie(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                MovieDBCrontract.MovieEntry._ID,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR
        };

        String selection = MovieDBCrontract.MovieEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR))
                    );
            cursor.close();
            return movie;
        }else {
            cursor.close();
            return null;
        }
    }

    public int updateMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE, movie.getMovieName());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR, movie.getMovieDirector());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH, movie.getLengthMinutes());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE, movie.getMovieType());
        values.put(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR, movie.getMovieYear());

        String selection = MovieDBCrontract.MovieEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getMovieId())};

        return db.update(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    public int deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = MovieDBCrontract.MovieEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getMovieId())};

        return db.delete(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    public ArrayList<Movie> getAllMoviesFullTitle(String fullTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();
        String[] projection = {
                MovieDBCrontract.MovieEntry._ID,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR
        };
        String selection = MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + "=?";
        String[] selectionArgs = {fullTitle};

        Cursor cursor = db.query(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR))
            );
            movieList.add(movie);
        }
        cursor.close();
        return movieList;
    }

    public ArrayList<Movie> getAllMoviesPartialTitle(String partialTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();
        String[] projection = {
                MovieDBCrontract.MovieEntry._ID,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR
        };
        String selection = MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE + " LIKE ?";
        String[] selectionArgs = {"%" + partialTitle + "%"};

        Cursor cursor = db.query(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR))
            );
            movieList.add(movie);
        }
        cursor.close();
        return movieList;
    }

    public ArrayList<Movie> getAllMoviesDirector(String director) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();
        String[] projection = {
                MovieDBCrontract.MovieEntry._ID,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE,
                MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR
        };
        String selection = MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR + "=?";
        String[] selectionArgs = {director};

        Cursor cursor = db.query(
                MovieDBCrontract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Movie movie = new Movie(
                    cursor.getInt(cursor.getColumnIndex(MovieDBCrontract.MovieEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_DIR)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_LENGTH)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(MovieDBCrontract.MovieEntry.COLUMN_NAME_MOVIE_YEAR))
            );
            movieList.add(movie);
        }
        cursor.close();
        return movieList;
    }
}
