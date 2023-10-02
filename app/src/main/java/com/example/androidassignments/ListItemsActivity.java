package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Objects;

public class ListItemsActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private Switch switch1;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ListItemsActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        imageButton = findViewById(R.id.imageButton);
        switch1 = findViewById(R.id.switch1);
        checkBox = findViewById(R.id.checkBox);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePictureIntent =
                        new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 100);
                }
            }
        });
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                String text = b ? "Switch is On" : "Switch is Off";
                int duration = b ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.DialogMessage)
                        .setTitle(R.string.DialogTitle)
                        .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", getString(R.string.MyResponse));
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("ListItemsActivity", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("ListItemsActivity", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("ListItemsActivity", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("ListItemsActivity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("ListItemsActivity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ListItemsActivity", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ListItemsActivity", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(intent.getExtras()).get("data");
            if (imageBitmap != null) {
                imageButton.setImageBitmap(imageBitmap);
            }
        }
    }
}