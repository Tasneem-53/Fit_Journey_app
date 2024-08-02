package com.daclink.fitjourney;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.fitjourney.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding binding;
private String mUserName;
private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean userExist = false;
                //getting the edit text from username and password
                mUserName = binding.usernameEditText.getText().toString();
                mPassword = binding.passwordEditText.getText().toString();
                if(!mUserName.isEmpty() && !mPassword.isEmpty()){
                    userExist= verifyUserName(mUserName, mPassword);
                }
                System.out.println(userExist);
                //its always allowing this to execute.... there is an error here
                if(userExist == true){
                Intent intent = new Intent(LoginActivity.this, WelcomeUserActivity.class);

                startActivity(intent);
                }
            }
        });

        binding.createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                }
            }
        });

    }
    //todo finish writing this method
    //this method will check with the database to see if user name exist
    private boolean verifyUserName(String userName, String password){
        return userName.equals("Adan") && (password.equals("123"));
    }

    //Intent factory for LoginActivity
    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}