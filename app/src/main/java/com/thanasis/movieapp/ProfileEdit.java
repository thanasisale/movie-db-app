package com.thanasis.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileEdit extends AppCompatActivity {
    EditText edUsername, edPassword, edConPassword;
    Button edButton;
    int userId;
    DatabaseManager movieDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        movieDbHelper = new DatabaseManager(getApplicationContext());

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        edConPassword = findViewById(R.id.edConPassword);

        edButton = findViewById(R.id.edButton);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                userId = 0;
            } else {
                userId = extras.getInt("CURRENT_USER_ID");
            }
        } else {
            userId = (int) savedInstanceState.getSerializable("CURRENT_USER_ID");
        }

        if(userId != -1) {
            String[] userCre = movieDbHelper.getCurrentUserCredentials(userId);
            edUsername.setText(userCre[0]);
            edPassword.setText(userCre[1]);
        }

        edButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, conPassword;

                username = edUsername.getText().toString();
                password = edPassword.getText().toString();
                conPassword = edConPassword.getText().toString();

                if(username.equals("")){
                    Toast.makeText(ProfileEdit.this, "Username Required", Toast.LENGTH_SHORT).show();
                }else if (password.equals("")){
                    Toast.makeText(ProfileEdit.this, "Password Required", Toast.LENGTH_SHORT).show();
                }else if(conPassword.equals("")){
                    Toast.makeText(ProfileEdit.this, "Password Confirmation is Required", Toast.LENGTH_SHORT).show();
                }else if(!conPassword.equals(password)){
                    Toast.makeText(ProfileEdit.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                }else {
                    // Check if username is not taken while it isn't his current username
                    Boolean takenUsername = movieDbHelper.usernameFreeExistingUser(userId, username);
                    if(!takenUsername){
                        int success = movieDbHelper.updateCredentials(userId, username, password);

                        if (success > 0){
                            Toast.makeText(ProfileEdit.this, "Updated!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ProfileEdit.this, MovieList.class);
                            i.putExtra("CURRENT_USER_ID", userId);
                            startActivity(i);
                        }else{
                            Toast.makeText(ProfileEdit.this, "Something went wrong. Try Again!", Toast.LENGTH_SHORT).show();
                            edConPassword.setText("");
                        }

                    } else {
                        Toast.makeText(ProfileEdit.this, "The username is taken. Try Again!", Toast.LENGTH_SHORT).show();
                        edConPassword.setText("");
                    }
                }
            }
        });

    }
}
