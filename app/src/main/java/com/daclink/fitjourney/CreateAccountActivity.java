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
import com.daclink.fitjourney.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private FitJourneyRepository repository;
    private String mUserName;
    private String mPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = FitJourneyRepository.getRepository(getApplication());

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        mUserName = binding.createUsernameEditText.getText().toString();
        mPassword = binding.createPasswordEditText.getText().toString();
        if(mUserName.isEmpty() || mPassword.isEmpty()){
            Toast.makeText(CreateAccountActivity.this, "ERROR Blank field(s).\nPlease check you have entered both fields", Toast.LENGTH_SHORT).show();
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(mUserName);
        //this is going to watch that object for updates
        //Hey database find this user it and this will wait for it to come back
        userObserver.observe(this,user -> {
            if(user == null){
                User newUser = new User(mUserName,mPassword);
                insertNewUser(newUser);
                startActivity(WelcomeUserActivity.welcomeIntentFactory(getApplicationContext(), newUser.getId()));
            } else {
                Toast.makeText(CreateAccountActivity.this, "ERROR user already exist! Please try to log in again. ", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void insertNewUser(User newUser) {
        Toast.makeText(CreateAccountActivity.this, "Before SUcess! New user created! Welcome : ", Toast.LENGTH_SHORT).show();

        repository.insertNewUser(newUser);
        Toast.makeText(CreateAccountActivity.this, "Success! New user created! Welcome : ", Toast.LENGTH_SHORT).show();


    }


    static Intent createAccountIntentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);


    }
}