package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.daclink.fitjourney.Database.FitJourneyDatabase;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHome();
            }
        });

        binding.changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangePassword();
            }
        });
    }

    private void handleChangePassword() {
        String username = binding.UsernameEditText.getText().toString();
        String currentPwd = binding.currentInputEditText.getText().toString();
        String newPwd = binding.newInputEditText.getText().toString();
        String confirmNewPwd = binding.confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(username) ||TextUtils.isEmpty(currentPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(confirmNewPwd)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPwd.equals(confirmNewPwd)) {
            Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);
       // userObserver.observe(this, new Observer<User>() {
           // @Override
           // public void onChanged(User user) {
        userObserver.observe(this,user -> {
                if (user == null) {
                    Toast.makeText(SettingsActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentPwd.equals(user.getPassword())) {
                        user.setPassword(newPwd);
                        updateUser(user);
                    } else {
                        Toast.makeText(SettingsActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
           // }
        });

    }

    private void updateUser(User user) {
        repository.updateUser(user);
        Toast.makeText(SettingsActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
    }

    private void navigateToHome() {
        Intent intent = new Intent(SettingsActivity.this, WelcomeUserActivity.class);
        startActivity(intent);
    }

}

