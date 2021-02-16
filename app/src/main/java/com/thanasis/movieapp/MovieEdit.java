package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MovieEdit extends AppCompatActivity {
    EditText edTitle, edDirector, edLength, edType, edYear;
    Button edShowAll;
    int userId, movieId;
    DatabaseManager movieDbHelper;
    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_edit);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        edTitle = findViewById(R.id.edTitle);
        edDirector = findViewById(R.id.edDirector);
        edLength = findViewById(R.id.edLength);
        edType = findViewById(R.id.edType);
        edYear = findViewById(R.id.edYear);

        edShowAll = findViewById(R.id.edShowAll);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                userId = -1;
                movieId = -1;
            } else {
                userId = extras.getInt("CURRENT_USER_ID");
                movieId = extras.getInt("CURRENT_MOVIE_ID");
            }
        } else {
            userId = (int) savedInstanceState.getSerializable("CURRENT_USER_ID");
            movieId = (int) savedInstanceState.getSerializable("CURRENT_MOVIE_ID");
        }

        if(movieId != -1){
            movie = movieDbHelper.getSingleMovie(movieId);
            edTitle.setText(movie.getMovieName());
            edDirector.setText(movie.getMovieDirector());
            edLength.setText(movie.getLengthMinutes());
            edType.setText(movie.getMovieType());
            edYear.setText(movie.getMovieYear());
        }

        edShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieEdit.this, MovieList.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }
        });

    }

    public void updateMovie(View view){
        int id = movieId;
        String title = edTitle.getText().toString();
        String director = edDirector.getText().toString();
        String length = edLength.getText().toString();
        String type = edType.getText().toString();
        String year = edYear.getText().toString();

        int success = 0;

        if(title.length() != 0 && director.length() !=0 &&
                length.length() != 0 && type.length() != 0 && year.length() != 0) {

            Movie newMovie = new Movie(id, title, director, length, type, year);
            if(newMovie != null){
                success = movieDbHelper.updateMovie(newMovie);
            }

            if (success > 0) {
                Toast.makeText(MovieEdit.this, "Movie updated Successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(MovieEdit.this, MovieList.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }else
                Toast.makeText(MovieEdit.this, "Something went wrong. Try Again!", Toast.LENGTH_LONG).show();

        }else
            Toast.makeText(MovieEdit.this, "Please fill ALL the fields.", Toast.LENGTH_LONG).show();
    }

    public void deleteMovie(View view){
        int success;
        success = movieDbHelper.deleteMovie(movie);
        if(success > 0){
            Toast.makeText(MovieEdit.this, "Movie Deleted!", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MovieEdit.this, MovieList.class);
            i.putExtra("CURRENT_USER_ID", userId);
            startActivity(i);
        }else {
            Toast.makeText(MovieEdit.this, "Something went wrong. Try Again!", Toast.LENGTH_LONG).show();
        }
    }
}
