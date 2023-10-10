package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button startChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        startChatButton = findViewById(R.id.buttonStartChat);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MainActivity", "User clicked Start Chat");
                Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("MainActivity", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("MainActivity", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("MainActivity", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("MainActivity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("MainActivity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("MainActivity", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("MainActivity", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 10) {
            Log.i("MainActivity", "Returned to MainActivity.onActivityResult");
        }
        if (resultCode == Activity.RESULT_OK) {
            String messagePassed = intent.getStringExtra("Response");
            if (messagePassed != null && messagePassed.length() > 0) {
                Toast.makeText(getApplicationContext(), "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}