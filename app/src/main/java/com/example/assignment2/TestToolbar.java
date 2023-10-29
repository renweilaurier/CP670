package com.example.assignment2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.androidassignments.ChatWindow;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidassignments.databinding.ActivityTestToolbarBinding;

import com.example.androidassignments.R;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    private String itemOneMessage = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TestToolbar", "onCreate");
        super.onCreate(savedInstanceState);
        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, itemOneMessage, Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("TestToolbar", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("TestToolbar", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("TestToolbar", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("TestToolbar", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("TestToolbar", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("TestToolbar", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("TestToolbar", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_two) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setTitle("Do you want to go back?");
            builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id == R.id.action_three) {
            Log.d("Toolbar", "Choice 3 selected");
            LayoutInflater inflater =getLayoutInflater();
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setView(inflater.inflate(R.layout.layout_custom_dialog, null)) ;
            builder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText newMessageEditText = ((Dialog) dialog).findViewById(R.id.editTextNewMessage);
                    String message = newMessageEditText.getText().toString();
                    Log.d("`TestToolbar`", "New Message: " + message);
                    if (message.matches("")) {
                        Toast.makeText(TestToolbar.this, getString(R.string.EmptyMessage), Toast.LENGTH_SHORT).show();
                    } else {
                        itemOneMessage = message;
                    }
                }
            });
            builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            builder.setView(inflater.inflate(R.layout.layout_custom_dialog, null)) ;
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id == R.id.action_about) {
            Toast.makeText(getApplicationContext(), "Version 1.0, by Wei Ren", Toast.LENGTH_SHORT).show();
        } else if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}