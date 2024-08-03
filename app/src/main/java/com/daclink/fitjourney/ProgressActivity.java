package com.daclink.fitjourney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.RoomSQLiteQuery;

import com.daclink.fitjourney.databinding.ActivityLoginBinding;
import com.daclink.fitjourney.databinding.ActivityMealsBinding;
import com.daclink.fitjourney.databinding.ActivityProgressBinding;

public class ProgressActivity extends AppCompatActivity {
    private ActivityProgressBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgressActivity.this, WelcomeUserActivity.class);
                startActivity(intent);
            }
        });




    }
}