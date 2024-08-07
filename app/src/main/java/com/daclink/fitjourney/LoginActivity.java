package com.daclink.fitjourney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.UserDAO;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivityLoginBinding;

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
                    startActivity(IntentFactory.createAccountIntentFactory(getApplicationContext()));
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
                if(mPassword.equals(user.getPassword())){
                    startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext(), user.getId()));
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this, "No user named found: " + mUserName, Toast.LENGTH_SHORT).show();
            }

        });

    }


}
