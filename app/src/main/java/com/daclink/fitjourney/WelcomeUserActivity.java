package com.daclink.fitjourney;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
        private static final String SHARED_PREFERENCE_USERID_KEY = "com.daclink.fitjourney.SHARED_PREFENCE_USERID_KEY";
        private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.daclink.fitjourney.SAVED_INSTANCE_STATE_USERID_KEY";
        private static final int LOGGED_OUT = -1;
        private static final String TAG = "AV_GYMLOG";
        private FitJourneyRepository repository;
        private User user;
        private int loggedInUserId = LOGGED_OUT;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome_user);

            repository = FitJourneyRepository.getRepository(getApplication());
            loginUser(savedInstanceState);

            if (loggedInUserId == LOGGED_OUT) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                finish();
                return;
            }

            updateSharedPreference();
            setupButtons();
        }

        private void updateSharedPreference() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
            editor.apply();
        }

        private void setupButtons() {
        }

        static Intent welcomeIntentFactory(Context context) {
            return new Intent(context, WelcomeUserActivity.class);
        }

        static Intent welcomeIntentFactory(Context context, int userId) {
            Intent intent = new Intent(context, WelcomeUserActivity.class);
            intent.putExtra(WELCOME_USER_ACTIVITY_USER_ID, userId);
            return intent;
        }

        private void loginUser(Bundle savedInstanceState) {
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

            if (loggedInUserId == LOGGED_OUT && savedInstanceState != null) {
                loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
            }

            if (loggedInUserId == LOGGED_OUT) {
                loggedInUserId = getIntent().getIntExtra(WELCOME_USER_ACTIVITY_USER_ID, LOGGED_OUT);
            }

            if (loggedInUserId == LOGGED_OUT) {
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
            finish();
        }

        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            MenuItem item = menu.findItem(R.id.logoutMenuItem);
            item.setVisible(true);
            if (user == null) {
                return false;
            }

            item.setTitle(user.getUsername());
            item.setOnMenuItemClickListener(menuItem -> {
                showLogoutDialog();
                return true;
            });

            return true;
        }

        private void showLogoutDialog() {
            new AlertDialog.Builder(this)
                    .setMessage("\t\t Logout of Fit Journey?")
                    .setPositiveButton("Logout", (dialog, which) -> logout())
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
            updateSharedPreference();
        }
    }