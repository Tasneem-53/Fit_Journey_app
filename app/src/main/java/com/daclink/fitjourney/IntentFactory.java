package com.daclink.fitjourney;


import static com.daclink.fitjourney.WelcomeUserActivity.WELCOME_USER_ACTIVITY_USER_ID;

import android.content.Context;
import android.content.Intent;

public class IntentFactory {
    //Intent factory for CreateAccountActivity
    static Intent createAccountIntentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);
    }
    //Intent factory for AdminActivity
    static Intent adminPageIntentFactory(Context context) {
        return new Intent(context, AdminPageActivity.class);
    }



    //Intent factory for LoginActivity
    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);

    }
    //Intent factory for WelcomeUserActivity with one parameter of a context
    static Intent welcomeIntentFactory(Context context) {
        return new Intent(context, WelcomeUserActivity.class);
    }
    //Intent factory for WelcomeUserActivity with two parameters of a context and int
    static Intent welcomeIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, WelcomeUserActivity.class);
        intent.putExtra(WELCOME_USER_ACTIVITY_USER_ID, userId);
        return intent;
    }

    //Intent factory for ProgressActivity
    static Intent progressIntentFactory(Context context) {
        return new Intent(context, ProgressActivity.class);
    }
    //Intent factory for Settings with two parameters of a context and int
    static Intent settingsIntentFactory(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    //Intent factory for MealsActivity
    static Intent mealsIntentFactory(Context context) {
        return new Intent(context, MealsActivity.class);
    }


    //Intent factory for Exercises
    static Intent exercisesIntentFactory(Context context) {
        return new Intent(context, ExercisesActivity.class);
    }





}
