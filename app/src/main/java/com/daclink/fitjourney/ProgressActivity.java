// com/daclink/fitjourney/ProgressActivity.java
package com.daclink.fitjourney;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.Database.entities.Meals;
import com.daclink.fitjourney.databinding.ActivityProgressBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProgressActivity extends AppCompatActivity {

    private ActivityProgressBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;
    private ProgressAdapter progressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor(); // ExecutorService

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressAdapter = new ProgressAdapter(new ArrayList<>()); // Initialize with an empty list
        binding.recyclerView.setAdapter(progressAdapter);

        executorService.execute(() -> {
            repository = new FitJourneyRepository(getApplication());
            runOnUiThread(this::fetchDataAndDisplay);
        });

        binding.homeButton.setOnClickListener(v -> {
            startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
        });
    }

    private void fetchDataAndDisplay() {
        executorService.execute(() -> {
            List<Exercise> exercises = repository.getAllExercises();
            List<Meals> meals = repository.getAllMeals();

            List<Object> progressList = new ArrayList<>();
            progressList.addAll(exercises);
            progressList.addAll(meals);

            runOnUiThread(() -> {
                progressAdapter = new ProgressAdapter(progressList);
                binding.recyclerView.setAdapter(progressAdapter);
                updateTotalSummary(progressList);
            });
        });
    }

    private void updateTotalSummary(List<Object> progressList) {
        double totalWeightLost = 0;
        double totalWeightGained = 0;

        for (Object item : progressList) {
            if (item instanceof Exercise) {
                totalWeightLost += calculateWeightLost((Exercise) item);
            } else if (item instanceof Meals) {
                totalWeightGained += calculateWeightGained((Meals) item);
            }
        }

        binding.totalSummaryTextView.setText(String.format(Locale.US, "Total weight lost: %.2f lbs\nTotal weight gained: %.2f lbs", totalWeightLost, totalWeightGained));
    }

    private double calculateWeightLost(Exercise exercise) {
        double caloriesBurned = exercise.getDuration() * 8;
        return caloriesBurned / 3500;
    }

    private double calculateWeightGained(Meals meal) {
        return meal.getCalories() / 3500;
    }
}
