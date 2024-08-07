package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.RoomSQLiteQuery;

import com.daclink.fitjourney.Database.FitJourneyDatabase;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivityAddUserAdminBinding;

public class AddUserAdminActivity extends AppCompatActivity {

    private ActivityAddUserAdminBinding binding;

    private FitJourneyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FitJourneyDatabase.getDatabase(getApplicationContext());


        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
            }
        });

        binding.createNewAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.newAdminLabelTextView.getText().toString().trim();
                String password = binding.newAdminPasswordLabelTextView.getText().toString().trim();

                if (username.isEmpty()) {
                    Toast.makeText(AddUserAdminActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }

                addAdmin(username, password);
            }
        });
    }
    private void addAdmin(String username, String password) {

        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            User existingAdmin = db.userDAO().getAdminByUsername(username);
            runOnUiThread(() -> {
                if (existingAdmin != null) {
                    Toast.makeText(this, "Admin already exists", Toast.LENGTH_SHORT).show();
                } else {
                    User newAdmin = new User(username, password);
                    newAdmin.setAdmin(true);
                    FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
                        db.userDAO().insert(newAdmin);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Admin added successfully", Toast.LENGTH_SHORT).show();
                        });
                    });
                }
            });
        });

        /*  User newAdmin = new User(username, password);
        newAdmin.setAdmin(true);
        FitJourneyDatabase.databaseWriteExecutor.execute(() -> {
            db.userDAO().insert(newAdmin);
            runOnUiThread(() -> {
                Toast.makeText(this, "Admin added successfully", Toast.LENGTH_SHORT).show();
            });
        });

  */  }
}