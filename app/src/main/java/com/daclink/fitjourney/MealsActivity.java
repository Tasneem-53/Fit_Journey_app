package com.daclink.fitjourney;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;

import java.time.LocalDate;
import java.util.List;


public class MealsActivity extends AppCompatActivity {

    private ActivityMealsBinding binding;
    private FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FitJourneyRepository(getApplication());

        binding.homeButton.setOnClickListener(v -> {
            finish(); // This will close the MealsActivity and go back to MainActivity
       });

        binding.submitButton.setOnClickListener(v -> {
            handleSubmit();
        });

        loadMeals();
    }

    // Process the data, e.g., save to the database
    private void handleSubmit() {
        String mealName = binding.mealNameEditText.getText().toString();
        String mealCalories = binding.mealCaloriesEditText.getText().toString();

        if (!mealName.isEmpty() && !mealCalories.isEmpty()) {
            double calories = Double.parseDouble(mealCalories);
            Meals meal = new Meals(mealName, calories, LocalDate.now());

            repository.insertMeal(meal);

            // Refresh the meal list
            loadMeals();

            String message = "Meal: " + mealName + "\nCalories: " + calories;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Clear fields
            binding.mealNameEditText.setText("");
            binding.mealCaloriesEditText.setText("");
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadMeals() {
        // Load the meals from the repository
        new Thread(() -> {
            List<Meals> mealsList = repository.getAllMeals();
            runOnUiThread(() -> {
                StringBuilder mealsText = new StringBuilder();
                for (Meals meal : mealsList) {
                    mealsText.append("Meal: ").append(meal.getMeal())
                            .append(", Calories: ").append(meal.getCalories())
                            .append(", Date: ").append(meal.getDate())
                            .append("\n");
                }
                binding.mealsDisplayTextView.setText(mealsText.toString());
            });
        }).start();
    }

}
