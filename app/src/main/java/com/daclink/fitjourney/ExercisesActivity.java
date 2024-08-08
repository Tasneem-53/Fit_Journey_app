// com/daclink/fitjourney/ExercisesActivity.java
package com.daclink.fitjourney;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.databinding.ActivityExercisesBinding;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExercisesActivity extends AppCompatActivity {

    private ActivityExercisesBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;
    private ExerciseAdapter exerciseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExercisesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor(); // Initialize ExecutorService

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(new ArrayList<>()); // Initialize with an empty list
        binding.recyclerView.setAdapter(exerciseAdapter);

        executorService.execute(() -> {
            repository = new FitJourneyRepository(getApplication());
            runOnUiThread(this::loadExercises); // Load after repository initialization
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
        String exerciseName = binding.exerciseNameEditText.getText().toString();
        String exerciseDuration = binding.exerciseDurationEditText.getText().toString();

        if (!exerciseName.isEmpty() && !exerciseDuration.isEmpty()) {
            double duration = Double.parseDouble(exerciseDuration);
            Exercise exercise = new Exercise(exerciseName, duration, LocalDate.now());

            executorService.execute(() -> {
                repository.insertExercise(exercise);
                runOnUiThread(this::loadExercises);
            });

            String message = "Exercise: " + exerciseName + "\nDuration: " + duration + " minutes";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Clear fields
            binding.exerciseNameEditText.setText("");
            binding.exerciseDurationEditText.setText("");
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Load the exercises from the repository
    private void loadExercises() {
        executorService.execute(() -> {
            List<Exercise> exercisesList = repository.getAllExercises();
            runOnUiThread(() -> {
                exerciseAdapter = new ExerciseAdapter(exercisesList);
                binding.recyclerView.setAdapter(exerciseAdapter);
            });
        });
    }
}
