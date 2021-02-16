package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText rgUsername, rgPassword, rgConPassword;
    Button rgButton;
    TextView rgLogin;


    DatabaseManager movieDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        rgUsername = findViewById(R.id.rgUsername);
        rgPassword = findViewById(R.id.rgPassword);
        rgConPassword = findViewById(R.id.rgConPassword);

        rgButton = findViewById(R.id.rgButton);
        rgLogin = findViewById(R.id.rgLogin);

        rgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, conPassword;

                username = rgUsername.getText().toString();
                password = rgPassword.getText().toString();
                conPassword = rgConPassword.getText().toString();

                if(username.equals("")){
                    Toast.makeText(Register.this, "Username Required", Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(Register.this, "Password Required", Toast.LENGTH_SHORT).show();
                }else if(conPassword.equals("")){
                    Toast.makeText(Register.this, "Password Confirmation is Required", Toast.LENGTH_SHORT).show();
                }else if(!conPassword.equals(password)){
                    Toast.makeText(Register.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }else {
                    // Check if the username already exists
                    Boolean takenUsername = movieDbHelper.usernameFree(username);
                    if(!takenUsername){
                        movieDbHelper.addNewUser(username, password);
                        Toast.makeText(Register.this, "Added!", Toast.LENGTH_SHORT).show();
                        // Redirect user back to login screen
                        Intent i = new Intent(Register.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "The username is taken!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
