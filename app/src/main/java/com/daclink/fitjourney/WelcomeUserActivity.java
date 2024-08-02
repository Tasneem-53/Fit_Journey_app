package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        //meals button
        Button mealsButton = findViewById(R.id.meals_button);
        mealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeUserActivity.this, MealsActivity.class);
                startActivity(intent);
            }
        });

        //exercises button
        Button exercisesButton = findViewById(R.id.exercises_button);
        exercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeUserActivity.this, ExercisesActivity.class);
                startActivity(intent);
            }
        });
    }
}