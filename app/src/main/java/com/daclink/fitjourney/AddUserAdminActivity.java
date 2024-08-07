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

import com.daclink.fitjourney.databinding.ActivityAddUserAdminBinding;

public class AddUserAdminActivity extends AppCompatActivity {
 private ActivityAddUserAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IntentFactory.welcomeIntentFactory(getApplicationContext()));
            }


        });
    }
}