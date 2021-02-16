package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MovieSearch extends AppCompatActivity {
    int userId;
    EditText sFTitle, sPTitle, sDirector;
    Button sFTitleBtn, sPTitleBtn, sDirectorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        sFTitle = findViewById(R.id.sFTitle);
        sPTitle = findViewById(R.id.sPTitle);
        sDirector = findViewById(R.id.sDirector);

        sFTitleBtn = findViewById(R.id.sFTitleBtn);
        sPTitleBtn = findViewById(R.id.sPTitleBtn);
        sDirectorBtn = findViewById(R.id.sDirectorBtn);

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

        // Set the on click listeners to change the activity to the list and pass the user search choice as intent extra
        sFTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullTitle = sFTitle.getText().toString();
                if(fullTitle.equals("")){
                    Toast.makeText(MovieSearch.this, "The title cannot be empty.", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(MovieSearch.this, MovieList.class);
                    i.putExtra("CURRENT_USER_ID", userId);
                    i.putExtra("FULL_MOVIE_TITLE", fullTitle);
                    startActivity(i);
                }
            }
        });

        sPTitleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partial = sPTitle.getText().toString();
                if(partial.equals("")){
                    Toast.makeText(MovieSearch.this, "The title cannot be empty.", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(MovieSearch.this, MovieList.class);
                    i.putExtra("CURRENT_USER_ID", userId);
                    i.putExtra("PARTIAL_MOVIE_TITLE", partial);
                    startActivity(i);
                }
            }
        });

        sDirectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String director = sDirector.getText().toString();
                if(director.equals("")){
                    Toast.makeText(MovieSearch.this, "The director's name cannot be empty.", Toast.LENGTH_LONG).show();
                }else {
                    Intent i = new Intent(MovieSearch.this, MovieList.class);
                    i.putExtra("CURRENT_USER_ID", userId);
                    i.putExtra("MOVIE_DIRECTOR", director);
                    startActivity(i);
                }
            }
        });

    }

}
