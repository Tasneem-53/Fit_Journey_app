package com.daclink.fitjourney;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

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
import androidx.room.Room;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private FitJourneyRepository repository;
    private String mUserName;
    private String mPassword;
    private LiveData<User> userObserver;  // Declare LiveData outside

    private int loggedInUserId;
    private static final String TAG = "SettingsActivity";
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
                startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
            }
        });

        binding.changeButton.setOnClickListener(v -> {
            resetPassword();
        });
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(getString(R.string.preference_userId_key), SettingsActivity.LOGGED_OUT);
    }

    private void resetPassword() {
        String username = binding.UsernameEditText.getText().toString();
        String currentPassword = binding.currentInputEditText.getText().toString();
        String newPassword = binding.newInputEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(SettingsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(SettingsActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Fetching user with username: " + username);

        // Observe user by username
        userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                Log.d(TAG, "User found: " + user.getUsername());
                Log.d(TAG, "Stored password: " + user.getPassword());
                Log.d(TAG, "Entered current password: " + currentPassword);
                if (user.getPassword().equals(currentPassword)) {
                    user.setPassword(newPassword);
                    repository.updateUser(user);
                    Toast.makeText(SettingsActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "User is null");
                Toast.makeText(SettingsActivity.this, "User not found", Toast.LENGTH_SHORT).show();
            }
            userObserver.removeObservers(this);
        });
    }


  /*  @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userObserver != null) {
            userObserver.removeObservers(this);  // Clean up observer
        }
    }*/

    static Intent SettingsIntentFactory(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}