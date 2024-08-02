package com.daclink.fitjourney;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.databinding.ActivityDeleteUserAdminBinding;
import com.daclink.fitjourney.databinding.ActivityMainBinding;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;

public class DeleteUserAdminActivity extends AppCompatActivity {

    ActivityDeleteUserAdminBinding binding;
    FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userLogTextView.setMovementMethod(new ScrollingMovementMethod());
        repository = new FitJourneyRepository(this);

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
        // Replace this with the actual delete logic from your repository
        boolean result = repository.deleteUser(userId); // This method should be implemented in your repository

        if (result) {
            Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
            binding.userLogTextView.append("User ID " + userId + " deleted\n");
        } else {
            Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show();
        }
    }
}