package com.daclink.fitjourney;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.daclink.fitjourney.Database.FitJourneyDatabase;
import com.daclink.fitjourney.Database.FitJourneyRepository;
import com.daclink.fitjourney.Database.entities.User;
import com.daclink.fitjourney.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private FitJourneyRepository repository;
    private String mUserName;
    private String mPassword;
    private LiveData<User> userObserver;  // Declare LiveData outside

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = FitJourneyRepository.getRepository(getApplication());

        binding.createAccountButton.setOnClickListener(v -> {
            createAccount();  // Trigger account creation
        });
    }

    private void createAccount() {
        mUserName = binding.createUsernameEditText.getText().toString();
        mPassword = binding.createPasswordEditText.getText().toString();
        if (mUserName.isEmpty() || mPassword.isEmpty()) {
            Toast.makeText(CreateAccountActivity.this, "ERROR Blank field(s).\nPlease check you have entered both fields", Toast.LENGTH_SHORT).show();
            return;
        }

            userObserver = repository.getUserByUsername(mUserName);
            userObserver.observe(this, user -> {
                if (user == null) {
                    User newUser = new User(mUserName, mPassword);
                    Toast.makeText(CreateAccountActivity.this, "Success! New user created! Login: " + mUserName, Toast.LENGTH_SHORT).show();
                    repository.insertNewUser(newUser);
                    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                } else {
                    Toast.makeText(CreateAccountActivity.this, "User named exists in database, please login: ", Toast.LENGTH_SHORT).show();
                }
            });
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userObserver != null) {
            userObserver.removeObservers(this);  // Clean up observer
        }
    }

    static Intent createAccountIntentFactory(Context context) {
        return new Intent(context, CreateAccountActivity.class);
    }
}