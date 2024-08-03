package com.daclink.fitjourney;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivityAddUserAdminBinding;

public class AddUserAdminActivity extends AppCompatActivity {

    ActivityAddUserAdminBinding binding;
    FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.adminUserLogTextView.setMovementMethod(new ScrollingMovementMethod());
        repository = new FitJourneyRepository(getApplication());

        binding.createNewAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdmin();
            }
        });
    }

    private void addAdmin() {
        String userName = binding.newAdminLabelTextView.getText().toString().trim();
        String userPassword = binding.newAdminPasswordLabelTextView.getText().toString().trim();

        if (userName.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, "Please enter a User Name and Password", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(userName, userPassword);
        newUser.setUsername(userName);
        newUser.setPassword(userPassword);

        repository.addUserAdmin(newUser, new FitJourneyRepository.RepositoryCallback() {
            @Override
            public void onComplete(final boolean success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            Toast.makeText(AddUserAdminActivity.this, "Admin added successfully", Toast.LENGTH_SHORT).show();
                            binding.adminUserLogTextView.append("Admin " + userName + " added\n");
                        } else {
                            Toast.makeText(AddUserAdminActivity.this, "Failed to add admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}