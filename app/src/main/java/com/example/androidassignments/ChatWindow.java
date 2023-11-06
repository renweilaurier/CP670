package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.utils.ChatDatabaseHelper;

public class ChatWindow extends AppCompatActivity {

    private EditText inputEditText;
    private ListView messageListView;
    private Button sendButton;

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private List<String> messageList;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        messageList = new ArrayList<>();
        dbHelper = new ChatDatabaseHelper(ChatWindow.this);
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(true, ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            Log.i("ChatWindow", "SQL MESSAGE: " + message);
            Log.i("ChatWindow", "Cursor's column count = " +cursor.getColumnCount() );
            messageList.add(message);
            cursor.moveToNext();
        }
        cursor.close();

        inputEditText = findViewById(R.id.editTextInput);
        ArrayAdapter<String> messageAdapter =
                new ChatAdapter(this, android.R.layout.simple_list_item_1, messageList);
        messageListView = (ListView) findViewById(R.id.listViewMessage);
        messageListView.setAdapter(messageAdapter);
        sendButton = findViewById(R.id.buttonSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = inputEditText.getText().toString();
                if (message.matches("")) {
                    Toast.makeText(ChatWindow.this, getString(R.string.EmptyMessage), Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                messageList.add(message);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()
                inputEditText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        public ChatAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
        }

        public ChatAdapter(@NonNull Context ctx) {
            super(ctx, 0);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                Log.i("ChatWindow", "chat_row_incoming");
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                Log.i("ChatWindow", "chat_row_outgoing");
            }
            TextView message = (TextView)result.findViewById(R.id.textViewMessage);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }

    @Override
    protected void onResume() {
        Log.i("ChatWindow", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("ChatWindow", "onCreate");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("ChatWindow", "onCreate");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("ChatWindow", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("ChatWindow", "onDestroy");
        super.onDestroy();
        database.close();
        dbHelper.close();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ChatWindow", "onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i("ChatWindow", "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }
}