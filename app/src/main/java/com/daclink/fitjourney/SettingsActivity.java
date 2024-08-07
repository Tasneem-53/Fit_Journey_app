package com.daclink.fitjourney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    FitJourneyRepository repository;

    private int loggedInUserId;

    private static final int LOGGED_OUT = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = FitJourneyRepository.getRepository(getApplication());

        loggedInUserId = getLoggedInUserId();

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });


        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(getString(R.string.preference_userId_key), SettingsActivity.LOGGED_OUT);
    }

    private void resetPassword() {
        String currentPassword = binding.currentInputEditText.getText().toString();
        String newPassword = binding.newInputEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(SettingsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(SettingsActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            if (user != null && user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                repository.updateUser(user);
                Toast.makeText(SettingsActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
            }
            userObserver.removeObservers(this);
        });
    }
    private void navigateToHome() {
        Intent intent = new Intent(SettingsActivity.this, WelcomeUserActivity.class);
        startActivity(intent);
    }
/*
    public static Intent settingsIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra(SettingsActivity.SETTINGS_ACTIVITY_USER_ID, userId);
        return intent;
    }*/

}

