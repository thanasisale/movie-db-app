package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {

    DatabaseManager movieDbHelper;
    ListView listMovies;
    Button lVAddButton, lVSearch, lVEditUser;
    int userId;
    String fullTitle, partialTitle, director;
    ArrayList<Movie> allMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        // Get the intent extras if they exist
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                userId = -1;
            } else {
                userId = extras.getInt("CURRENT_USER_ID");
                fullTitle = extras.getString("FULL_MOVIE_TITLE");
                partialTitle = extras.getString("PARTIAL_MOVIE_TITLE");
                director = extras.getString("MOVIE_DIRECTOR");
            }
        } else {
            userId = (int) savedInstanceState.getSerializable("CURRENT_USER_ID");
            fullTitle = (String) savedInstanceState.getSerializable("FULL_MOVIE_TITLE");
            partialTitle = (String) savedInstanceState.getSerializable("PARTIAL_MOVIE_TITLE");
            director = (String) savedInstanceState.getSerializable("MOVIE_DIRECTOR");
        }

        listMovies = findViewById(R.id.listView);

        lVAddButton = findViewById(R.id.lVAddButton);
        lVSearch = findViewById(R.id.lVSearch);
        lVEditUser = findViewById(R.id.lVEditUser);

        lVAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieList.this, AddMovie.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }
        });

        lVEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieList.this, ProfileEdit.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }
        });

        lVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieList.this, MovieSearch.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }
        });

        // Use the appropriate method to fill the movies array based on the extra we got.
        if(fullTitle != null)
            allMovies = movieDbHelper.getAllMoviesFullTitle(fullTitle);
        else if(partialTitle != null)
            allMovies = movieDbHelper.getAllMoviesPartialTitle(partialTitle);
        else if(director != null)
            allMovies = movieDbHelper.getAllMoviesDirector(director);
        else
            allMovies = movieDbHelper.getAllMovies();

        if (allMovies != null) {
            MovieListAdapter adapter = new MovieListAdapter(this, R.layout.adapter_view_layout, allMovies);
            listMovies.setAdapter(adapter);

            listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MovieList.this, allMovies.get(position).getMovieName() , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MovieList.this, MovieEdit.class);
                    i.putExtra("CURRENT_MOVIE_ID", allMovies.get(position).getMovieId());
                    i.putExtra("CURRENT_USER_ID", userId);
                    startActivity(i);
                }
            });
        }
    }

}
