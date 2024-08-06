package com.daclink.fitjourney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

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

        repository = FitJourneyRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
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
    private void verifyUser() {
        boolean userExist = false;
        //getting the edit text from username and password
        mUserName = binding.usernameEditText.getText().toString();
        mPassword = binding.passwordEditText.getText().toString();

        if(mUserName.isEmpty()){
            Toast.makeText(LoginActivity.this, "User name can not be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(mUserName);
        //this is going to watch that object for updates
        //Hey database find this user it and this will wait for it to come back
        userObserver.observe(this,user -> {
            if(user != null){
                Toast.makeText(LoginActivity.this, "Inside user user is not null", Toast.LENGTH_SHORT).show();

                if(mPassword.equals(user.getPassword())){
                    Toast.makeText(LoginActivity.this, "Password is correct", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, "Both username and password are correct " + user.getUsername() + " and " + user.getPassword(), Toast.LENGTH_SHORT).show();

                    startActivity(WelcomeUserActivity.welcomeIntentFactory(getApplicationContext(), user.getId()));
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(LoginActivity.this, "No user named found: " + mUserName, Toast.LENGTH_SHORT).show();

            }

        });

    }


    //Intent factory for LoginActivity
    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);


    }
}
    /*
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

     */