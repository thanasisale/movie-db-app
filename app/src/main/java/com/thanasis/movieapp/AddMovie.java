package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {
    DatabaseManager movieDbHelper;
    Button nmAddButton, nmShowAll;
    EditText nmTitle, nmDirector, nmLength, nmType, nmYear;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        nmTitle = findViewById(R.id.nmTitle);
        nmDirector = findViewById(R.id.nmDirector);
        nmLength = findViewById(R.id.nmLength);
        nmType = findViewById(R.id.nmType);
        nmYear = findViewById(R.id.nmYear);

        nmAddButton = findViewById(R.id.nmAddButton);
        nmShowAll = findViewById(R.id.nmShowAll);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                userId = -1;
            } else {
                userId = extras.getInt("CURRENT_USER_ID");
            }
        } else {
            userId = (int) savedInstanceState.getSerializable("CURRENT_USER_ID");
        }

        nmShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddMovie.this, MovieList.class);
                i.putExtra("CURRENT_USER_ID", userId);
                startActivity(i);
            }
        });
    }

    public void addNew(View view){
        String title = nmTitle.getText().toString();
        String director = nmDirector.getText().toString();
        String length = nmLength.getText().toString();
        String type = nmType.getText().toString();
        String year = nmYear.getText().toString();

        if(title.length() != 0 && director.length() !=0 &&
                length.length() != 0 && type.length() != 0 && year.length() != 0) {

            Movie newMovie = new Movie(title, director, length, type, year);
            boolean success = movieDbHelper.addMovie(newMovie);
            if (success)
                Toast.makeText(AddMovie.this, "Movie Added Successfully", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(AddMovie.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            nmTitle.setText("");
            nmDirector.setText("");
            nmLength.setText("");
            nmType.setText("");
            nmYear.setText("");
        }else
            Toast.makeText(AddMovie.this, "Please Fill ALL The Fields", Toast.LENGTH_LONG).show();
    }
}
