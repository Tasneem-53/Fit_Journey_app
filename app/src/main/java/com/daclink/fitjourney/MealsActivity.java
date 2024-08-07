package com.daclink.fitjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealsActivity extends AppCompatActivity {

    private ActivityMealsBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor(); // Initialize ExecutorService

        executorService.execute(() -> {
            repository = new FitJourneyRepository(getApplication());
            runOnUiThread(this::loadMeals); // Load after repository initialization
        });

        binding.homeButton.setOnClickListener(v -> {
            finish(); // Close the MealsActivity
        });

        binding.submitButton.setOnClickListener(v -> {
            handleSubmit();
        });
    }

    // Save to the database
    private void handleSubmit() {
        String mealName = binding.mealNameEditText.getText().toString();
        String mealCalories = binding.mealCaloriesEditText.getText().toString();

        if (!mealName.isEmpty() && !mealCalories.isEmpty()) {
            double calories = Double.parseDouble(mealCalories);
            Meals meal = new Meals(mealName, calories, LocalDate.now());

            executorService.execute(() -> {
                repository.insertMeal(meal);
                runOnUiThread(this::loadMeals);
            });

            String message = "Meal: " + mealName + "\nCalories: " + calories;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Clear fields
            binding.mealNameEditText.setText("");
            binding.mealCaloriesEditText.setText("");
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    //Load the meals from the repository
    private void loadMeals() {
        executorService.execute(() -> {
            List<Meals> mealsList = repository.getAllMeals();
            runOnUiThread(() -> {
                updateMealsLayout(mealsList);
            });
        });
    }

    private void updateMealsLayout(List<Meals> mealsList) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = binding.mealsLayout;

        layout.removeAllViews();

        for (Meals meal : mealsList) {
            View itemView = inflater.inflate(R.layout.item_meal, layout, false);
            ((TextView) itemView.findViewById(R.id.titleTextView)).setText(String.format(Locale.US, "Meal: %s", meal.getMeal()));
            ((TextView) itemView.findViewById(R.id.detailsTextView)).setText(String.format(Locale.US, "Date: %s\nCalories: %.2f",
                    meal.getDate(), meal.getCalories()));
            layout.addView(itemView);
        }
    }
}
