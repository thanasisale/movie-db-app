package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText lgnUsername, lgnPassword;
    Button lgnButton;
    TextView lgnRegister;

    DatabaseManager movieDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lgnUsername = findViewById(R.id.lgnUsername);
        lgnPassword = findViewById(R.id.lgnPassword);

        lgnButton = findViewById(R.id.lgnButton);
        lgnRegister = findViewById(R.id.lgnRegister);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        lgnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;

                username = lgnUsername.getText().toString();
                password = lgnPassword.getText().toString();

                if(username.equals("")){
                    Toast.makeText(MainActivity.this, "Username Required", Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(MainActivity.this, "Password Required", Toast.LENGTH_SHORT).show();
                }else {
                    // Validation Process
                    int userId = movieDbHelper.checkUserExists(username, password);
                    if(userId > 0){
                        Toast.makeText(MainActivity.this, String.valueOf(userId), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, MovieList.class);
                        i.putExtra("CURRENT_USER_ID", userId);
                        startActivity(i);
                    }else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        lgnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
                finish();
            }
        });

    }
}
