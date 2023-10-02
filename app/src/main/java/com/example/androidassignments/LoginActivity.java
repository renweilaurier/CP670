package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editLoginName;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("LoginActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editLoginName = findViewById(R.id.editLoginName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editLoginName.setText(sharedPreferences.getString("DefaultEmail", null));
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = String.valueOf(editLoginName.getText());
                String password = String.valueOf(editTextPassword.getText());
                if (!email.matches(emailPattern) || email.length() == 0) {
                    print(getString(R.string.InvalidEmail));
                    return;
                } else {
                    sharedPreferences.edit().putString("DefaultEmail", email).apply();
                }
                if (password.length() == 0) {
                    print(getString(R.string.BlankPassword));
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("LoginActivity", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("LoginActivity", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("LoginActivity", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("LoginActivity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("LoginActivity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("LoginActivity", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("LoginActivity", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void print(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}