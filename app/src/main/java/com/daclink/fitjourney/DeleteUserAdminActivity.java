package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyDatabase;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.databinding.ActivityDeleteUserAdminBinding;

public class DeleteUserAdminActivity extends AppCompatActivity {

    ActivityDeleteUserAdminBinding binding;
    FitJourneyRepository repository;

    private FitJourneyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FitJourneyDatabase.getDatabase(getApplicationContext());

        binding.homeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
            }
        });

        binding.userAdminDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.userAdminEditText.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(DeleteUserAdminActivity.this, "Please enter a User ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                deleteUser(username);
            }
        });

    }

    private void deleteUser(String username) {
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            int rowsDeleted = db.userDAO().deleteUserByUsername(username);
            runOnUiThread(() -> {
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            });
        });

        }

}