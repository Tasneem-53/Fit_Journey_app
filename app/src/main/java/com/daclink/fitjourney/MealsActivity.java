package com.daclink.fitjourney;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;

public class MealsActivity extends AppCompatActivity {

    ActivityMealsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handle the Home button click to navigate back to MainActivity
        binding.homeButton.setOnClickListener(v -> {
            finish(); // This will close the MealsActivity and go back to MainActivity
        });

        binding.submitButton.setOnClickListener(v -> {
            // Add functionality for the submit button
        });
    }
}
