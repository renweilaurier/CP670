package com.example.androidassignments;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListItemsActivityTest {

    private ListItemsActivity activity = new ListItemsActivity();

    @Test
    public void onCreate() {
        activity.onCreate(null);
    }

    @Test
    public void onResume() {
        activity.onResume();
    }

    @Test
    public void onStart() {
        activity.onStart();
    }

    @Test
    public void onPause() {
        activity.onPause();
    }

    @Test
    public void onStop() {
        activity.onStop();
    }

    @Test
    public void onDestroy() {
        activity.onDestroy();
    }

    @Test
    public void onSaveInstanceState() {
        activity.onSaveInstanceState(null);
    }

    @Test
    public void onRestoreInstanceState() {
        activity.onRestoreInstanceState(null);
    }

    @Test
    public void onActivityResult() {
        activity.onActivityResult(0, 0, null);
    }
}