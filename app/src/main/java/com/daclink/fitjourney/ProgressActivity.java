package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.databinding.ActivityProgressBinding;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressActivity extends AppCompatActivity {
    private ActivityProgressBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the repository
        repository = new FitJourneyRepository(getApplication());
        executorService = Executors.newSingleThreadExecutor();

        // Fetch data and update UI
        fetchDataAndDisplay();

        binding.homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, WelcomeUserActivity.class);
            startActivity(intent);
        });
    }

    private void fetchDataAndDisplay() {
        executorService.execute(() -> {
            List<Exercise> exercises = repository.getAllExercises();
            List<Meals> meals = repository.getAllMeals();

            ProgressData progressData = new ProgressData(exercises, meals);
            runOnUiThread(() -> updateProgressLayout(progressData));
        });
    }

    /**
     * Calculates the weight lost from an exercise based on calories burned.
     * Assumes 3500 calories = 1 pound.
     * 8 calories burned per minute
     */
    private double calculateWeightLost(Exercise exercise) {
        double caloriesBurned = exercise.getDuration() * 8;
        return caloriesBurned / 3500;
    }

    /**
     * Calculates the weight gained from a meal based on calories consumed.
     * Assumes 3500 calories = 1 pound.
     */
    private double calculateWeightGained(Meals meal) {
        return meal.getCalories() / 3500;
    }

    //Updates the UI with progress data by inflating views for each exercise and meal.
    private void updateProgressLayout(ProgressData progressData) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = binding.progressLayout;

        layout.removeAllViews();

        double totalWeightLost = 0;
        double totalWeightGained = 0;

        for (Exercise exercise : progressData.exercises) {
            double weightLost = calculateWeightLost(exercise);
            totalWeightLost += weightLost;
            View itemView = inflater.inflate(R.layout.item_progress, layout, false);
            ((TextView) itemView.findViewById(R.id.titleTextView)).setText(String.format(Locale.US, "Exercise: %s", exercise.getName()));
            ((TextView) itemView.findViewById(R.id.detailsTextView)).setText(String.format(Locale.US, "Date: %s\nDuration: %.2f mins\nWeight Lost: %.2f lbs",
                    exercise.getDate(), exercise.getDuration(), weightLost));
            layout.addView(itemView);
        }

        for (Meals meal : progressData.meals) {
            double weightGained = calculateWeightGained(meal);
            totalWeightGained += weightGained;
            View itemView = inflater.inflate(R.layout.item_progress, layout, false);
            ((TextView) itemView.findViewById(R.id.titleTextView)).setText(String.format(Locale.US, "Meal: %s", meal.getMeal()));
            ((TextView) itemView.findViewById(R.id.detailsTextView)).setText(String.format(Locale.US, "Date: %s\nCalories: %.2f\nWeight Gained: %.2f lbs",
                    meal.getDate(), meal.getCalories(), weightGained));
            layout.addView(itemView);
        }

        ((TextView) binding.totalSummaryTextView).setText(String.format(Locale.US, "Total weight lost: %.2f lbs\nTotal weight gained: %.2f lbs", totalWeightLost, totalWeightGained));
    }

    private static class ProgressData {
        List<Exercise> exercises;
        List<Meals> meals;

        ProgressData(List<Exercise> exercises, List<Meals> meals) {
            this.exercises = exercises;
            this.meals = meals;
        }
    }
}
