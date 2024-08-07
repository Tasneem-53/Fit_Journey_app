package com.daclink.fitjourney;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

        binding.cancelButton.setOnClickListener(v -> {
            startActivity(IntentFactory.loginIntentFactory(getApplicationContext()));  // Trigger account creation
        });
    }

    private void createAccount() {
        mUserName = binding.createUsernameEditText.getText().toString();
        mPassword = binding.createPasswordEditText.getText().toString();
        if (mUserName.isEmpty() || mPassword.isEmpty()) {
            Toast.makeText(CreateAccountActivity.this, "ERROR blank field(s).", Toast.LENGTH_SHORT).show();
            return;
        }

            userObserver = repository.getUserByUsername(mUserName);
            userObserver.observe(this, user -> {
                if (user == null) {
                    User newUser = new User(mUserName, mPassword);
                    Toast.makeText(CreateAccountActivity.this, "Success! New user created! Welcome  " + mUserName + "!", Toast.LENGTH_SHORT).show();
                    repository.insertNewUser(newUser);
                    startActivity(IntentFactory.loginIntentFactory(getApplicationContext()));
                } else {
                    Toast.makeText(CreateAccountActivity.this, "User named exists in database, please login. ", Toast.LENGTH_SHORT).show();
                    startActivity(IntentFactory.loginIntentFactory(getApplicationContext()));
                }
            });
        }

    //after observer is finish destroy it
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userObserver != null) {
            userObserver.removeObservers(this);
        }
    }


}