package com.daclink.fitjourney;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.databinding.ActivityDeleteUserAdminBinding;

public class DeleteUserAdminActivity extends AppCompatActivity {

    ActivityDeleteUserAdminBinding binding;
    FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userLogTextView.setMovementMethod(new ScrollingMovementMethod());

        repository = new FitJourneyRepository(getApplication());

        binding.userAdminDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void deleteUser() {
        String userId = binding.userAdminEditText.getText().toString().trim();
        if (userId.isEmpty()) {
            Toast.makeText(this, "Please enter a User ID", Toast.LENGTH_SHORT).show();
            return;
        }
        int username;
        try {
            username = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
            return;
        }
        repository.deleteUser(username, new FitJourneyRepository.RepositoryCallback() {
            @Override
            public void onComplete(final boolean success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            Toast.makeText(DeleteUserAdminActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            binding.userLogTextView.append("User ID " + userId + " deleted\n");
                        } else {
                            Toast.makeText(DeleteUserAdminActivity.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}