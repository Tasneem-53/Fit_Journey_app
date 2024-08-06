package com.daclink.fitjourney;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;

public class WelcomeUserActivity extends AppCompatActivity {
    private static final String WELCOME_USER_ACTIVITY_USER_ID = "com.daclink.fitjourney.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.daclink.fitjourney.SHARED_PREFENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.daclink.fitjourney.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    public static final String TAG = "AV_GYMLOG";
    private FitJourneyRepository repository;
    private User user;
    private int loggedInUserId = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);
        repository = FitJourneyRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        if(loggedInUserId == LOGGED_OUT){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreference();



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

        //progress button
        Button progressButton = findViewById(R.id.progress_button);
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeUserActivity.this, ProgressActivity.class);
                startActivity(intent);
            }
        });
        //Setting button
        Button settingButton = findViewById(R.id.settings_button);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeUserActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button adminButton = findViewById(R.id.admin_button);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeUserActivity.this, AdminPageActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor= sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();

    }

    static Intent welcomeIntentFactory(Context context) {
        return new Intent(context, WelcomeUserActivity.class);
    }
    static Intent welcomeIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(WELCOME_USER_ACTIVITY_USER_ID, userId);
        return new Intent(context, WelcomeUserActivity.class);
    }

    private void loginUser(Bundle savedInstanceState){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        // if(sharedPreferences.contains(SHARED_PREFERENCE_USERID_VALUE)){
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);
        // }
        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(WELCOME_USER_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
            }
        });
    }

    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();

        getIntent().putExtra(WELCOME_USER_ACTIVITY_USER_ID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        //todo check to see if this works

        item.setTitle(user.getUsername());

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }
    private void showLogoutDialog() {
        Log.d(TAG, "MADE int in showLogoutDialog");

        AlertDialog.Builder alterBuilder = new AlertDialog.Builder(WelcomeUserActivity.this);
        final AlertDialog alertDialog = alterBuilder.create();

        alterBuilder.setMessage("You want to Logout? ");

        alterBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alterBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alterBuilder.create().show();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();

    }


    }