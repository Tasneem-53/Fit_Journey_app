package com.daclink.fitjourney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.databinding.ActivityExercisesBinding;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExercisesActivity extends AppCompatActivity {

    private ActivityExercisesBinding binding;
    private FitJourneyRepository repository;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExercisesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor(); // Initialize ExecutorService

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
                updateExercisesLayout(exercisesList);
            });
        });
    }

    private void updateExercisesLayout(List<Exercise> exercisesList) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = binding.exercisesLayout;

        layout.removeAllViews();

        for (Exercise exercise : exercisesList) {
            View itemView = inflater.inflate(R.layout.item_exercise, layout, false);
            ((TextView) itemView.findViewById(R.id.titleTextView)).setText(String.format(Locale.US, "Exercise: %s", exercise.getName()));
            ((TextView) itemView.findViewById(R.id.detailsTextView)).setText(String.format(Locale.US, "Date: %s\nDuration: %.2f minutes",
                    exercise.getDate(), exercise.getDuration()));
            layout.addView(itemView);
        }
    }
}
