// com/daclink/fitjourney/MealsActivity.java
package com.daclink.fitjourney;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealsActivity extends AppCompatActivity {

    private ActivityMealsBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;
    private MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor(); // Initialize ExecutorService

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealAdapter = new MealAdapter(new ArrayList<>()); // Initialize with an empty list
        binding.recyclerView.setAdapter(mealAdapter);

        executorService.execute(() -> {
            repository = new FitJourneyRepository(getApplication());
            runOnUiThread(this::loadMeals); // Load after repository initialization
        });

        binding.homeButton.setOnClickListener(v -> {
            startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
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

    // Load the meals from the repository
    private void loadMeals() {
        executorService.execute(() -> {
            List<Meals> mealsList = repository.getAllMeals();
            runOnUiThread(() -> {
                mealAdapter = new MealAdapter(mealsList);
                binding.recyclerView.setAdapter(mealAdapter);
            });
        });
    }
}
