package com.daclink.fitjourney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.UserDAO;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding binding;
private FitJourneyRepository repository;
private String mUserName;
private String mPassword;
UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository =  FitJourneyRepository.getRepository(getApplication());
        binding.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean userExist = false;
                //getting the edit text from username and password
                mUserName = binding.usernameEditText.getText().toString();
                mPassword = binding.passwordEditText.getText().toString();

                try {
                    if (verifyUserName(mUserName, mPassword)) {
                        Intent intent = new Intent(LoginActivity.this, WelcomeUserActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(MainActivity.TAG, "Error verifying username 49", e);
                    Toast.makeText(LoginActivity.this, "Verification error 50", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                }
            }
        });

    }
    //todo finish writing this method
    //this method will check with the database to see if user name exist
    private boolean verifyUserName(String userName, String password) {
        Log.i(MainActivity.TAG, "Verifying username!");

        User user = repository.getUserName(userName);
        Log.i(MainActivity.TAG, "User retrieved: " + user);

        return user != null && user.getPassword().equals(password);
    }

    //Intent factory for LoginActivity
    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}