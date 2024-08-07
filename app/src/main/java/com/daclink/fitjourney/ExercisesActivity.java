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

public class ExercisesActivity extends AppCompatActivity {

    private ActivityExercisesBinding binding;
    private FitJourneyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExercisesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new FitJourneyRepository(getApplication());

        binding.homeButton.setOnClickListener(v -> {
            finish(); // Close the ExercisesActivity
        });

        binding.submitButton.setOnClickListener(v -> {
            handleSubmit();
        });

        loadExercises();
    }

    // Save to the database
    private void handleSubmit() {
        String exerciseName = binding.exerciseNameEditText.getText().toString();
        String exerciseDuration = binding.exerciseDurationEditText.getText().toString();

        if (!exerciseName.isEmpty() && !exerciseDuration.isEmpty()) {
            double duration = Double.parseDouble(exerciseDuration);
            Exercise exercise = new Exercise(exerciseName, duration, LocalDate.now());

            repository.insertExercise(exercise);

            // Refresh the exercise list
            loadExercises();

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
        new Thread(() -> {
            List<Exercise> exercisesList = repository.getAllExercises();
            runOnUiThread(() -> {
                updateExercisesLayout(exercisesList);
            });
        }).start();
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
