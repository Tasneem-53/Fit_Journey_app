package com.daclink.fitjourney;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.Exercise;
import com.daclink.fitjourney.databinding.ActivityExercisesBinding;

import java.time.LocalDate;
import java.util.List;

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
            finish(); // Close the ExercisesActivity and go back to MainActivity
        });

        binding.submitButton.setOnClickListener(v -> {
            handleSubmit();
        });

        loadExercises();
    }

    //Process & Save the data, refresh and reset.
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
                StringBuilder exercisesText = new StringBuilder();
                for (Exercise exercise : exercisesList) {
                    exercisesText.append("Exercise: ").append(exercise.getName())
                            .append(", Duration: ").append(exercise.getDuration())
                            .append(" minutes, Date: ").append(exercise.getDate())
                            .append("\n");
                }
                binding.exercisesDisplayTextView.setText(exercisesText.toString());
            });
        }).start();
    }

}
